package org.mrfyo.extractor.message;

import cn.hutool.core.util.ReflectUtil;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.annotation.ExtraMessage;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.bean.MessageDescriptor;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.factory.MessageDescriptorFactory;
import org.mrfyo.extractor.bean.ExtraFieldDescriptor;
import org.mrfyo.extractor.type.TypeHandlerAggregator;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.mrfyo.extractor.util.ReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 附加消息字段处理器
 *
 * @author Feyon
 * @date 2021/8/3
 */
public class ExtraMessageExtractor implements MessageExtractor {

    private final Logger log = LoggerFactory.getLogger(ExtraMessageExtractor.class);

    private final TypeHandlerAggregator fieldExtractor;

    private final MessageDescriptorFactory descriptorFactory;


    public ExtraMessageExtractor(TypeHandlerAggregator extractor, MessageDescriptorFactory descriptorFactory) {
        this.fieldExtractor = extractor;
        this.descriptorFactory = descriptorFactory;
    }

    @Override
    public boolean supported(MessageDescriptor<?> descriptor) {
        return descriptor.hasAnnotation(ExtraMessage.class);
    }

    @Override
    public Object unmarshal(Reader reader, MessageDescriptor<?> descriptor) {
        Object instance = ReflectUtil.newInstance(descriptor.getJavaType());

        ExtraMessage extraMessage = descriptor.getAnnotation(ExtraMessage.class);
        DataType keyDataType = extraMessage.keyDataType();
        DataType lengthDataType = extraMessage.lengthDataType();

        List<FieldDescriptor> fieldDescriptors = descriptor.getFieldDescriptors();
        Map<Integer, ExtraFieldDescriptor> descriptorMap = fieldDescriptors.stream()
                .map(fd -> (ExtraFieldDescriptor) fd)
                .collect(Collectors.toMap(ExtraFieldDescriptor::getId, ed -> ed));

        try {
            int minSize = keyDataType.getSize() + lengthDataType.getSize();
            StringJoiner joiner = new StringJoiner(",", "[", "]");
            while (reader.isReadable(minSize)) {
                int id = ReaderUtil.readInt(reader, keyDataType);
                int len = ReaderUtil.readInt(reader, lengthDataType);
                Reader r = reader.readBytes(len);
                try {
                    if (descriptorMap.containsKey(id)) {
                        ExtraFieldDescriptor fd = descriptorMap.get(id);
                        int size = fd.getSize();
                        try {
                            fd.setSize(r.readableBytes());
                            Object val = fieldExtractor.unmarshal(r, fd);
                            ReflectUtil.invoke(instance, fd.getWriteMethod(), val);
                        } finally {
                            fd.setSize(size);
                        }
                    } else {
                        joiner.add(Integer.toHexString(id));
                        log.warn("unknown sub message 0x{}", joiner);
                    }
                } finally {
                    r.release();
                }
            }
        } catch (Exception e) {
            throw new ExtractException(e);
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unmarshal(Reader reader, Class<T> clazz) {
        return (T) unmarshal(reader, descriptorFactory.getMessageDescriptor(clazz));
    }

    @Override
    public void marshal(Writer writer, MessageDescriptor<?> descriptor, Object bean) {
        List<FieldDescriptor> descriptors = descriptor.getFieldDescriptors();
        for (FieldDescriptor fd : descriptors) {
            ExtraFieldDescriptor efd = (ExtraFieldDescriptor) fd;
            Object value = ReflectUtil.invoke(bean, fd.getReadMethod());
            if (value == null) {
                continue;
            }
            writer.writeUint16(efd.getId());
            writer.writeUint8(efd.getSize());
            int wi = writer.writeIndex();
            try {
                fieldExtractor.marshal(writer, efd, value);
                if (efd.getSize() == 0) {
                    writer.setUint8(wi - 1, writer.writeIndex() - wi);
                }
            } catch (Exception e) {
                log.error("[encode] id is {}, field is {}; nest message is {}",
                        Integer.toHexString(descriptor.getId()), fd.getName(), e.getMessage());
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void marshal(Writer writer, T bean) {
        marshal(writer, descriptorFactory.getMessageDescriptor((Class<T>) bean.getClass()), bean);
    }
}
