package org.mrfyo.protocol.extractor.extractor;

import cn.hutool.core.util.ReflectUtil;
import org.mrfyo.protocol.extractor.MessageType;
import org.mrfyo.protocol.extractor.bean.ExtraFieldDescriptor;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.bean.MessageDescriptor;
import org.mrfyo.protocol.extractor.factory.MessageDescriptorFactory;
import org.mrfyo.protocol.extractor.type.TypeHandlerAggregator;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;
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
        return descriptor.getMessageType() == MessageType.EXTRA;
    }

    @Override
    public Object unmarshal(Reader reader, MessageDescriptor<?> descriptor) {
        Object instance = ReflectUtil.newInstance(descriptor.getJavaType());

        List<FieldDescriptor> fieldDescriptors = descriptor.getFieldDescriptors();
        Map<Integer, ExtraFieldDescriptor> descriptorMap = fieldDescriptors.stream()
                .map(fd -> (ExtraFieldDescriptor) fd)
                .collect(Collectors.toMap(ExtraFieldDescriptor::getId, ed -> ed));

        try {
            // ID 2 byte + LENGTH 1 byte
            int minSize = 3;
            StringJoiner joiner = new StringJoiner(",", "[", "]");
            while (reader.isReadable(minSize)) {
                int id = reader.readUint16();
                int len = reader.readUint8();
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
            throw new RuntimeException(e);
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
