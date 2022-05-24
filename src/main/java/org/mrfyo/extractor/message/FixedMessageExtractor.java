package org.mrfyo.extractor.message;

import cn.hutool.core.util.ReflectUtil;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.MessageType;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.bean.MessageDescriptor;
import org.mrfyo.extractor.factory.MessageDescriptorFactory;
import org.mrfyo.extractor.bean.BasicFieldDescriptor;
import org.mrfyo.extractor.type.TypeHandlerAggregator;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Feyon
 * @date 2021/8/2
 */
public class FixedMessageExtractor implements MessageExtractor {

    private final Logger log = LoggerFactory.getLogger(FixedMessageExtractor.class);

    private final TypeHandlerAggregator fieldExtractor;

    private final MessageDescriptorFactory descriptorFactory;

    public FixedMessageExtractor(TypeHandlerAggregator extractor, MessageDescriptorFactory descriptorFactory) {
        this.fieldExtractor = extractor;
        this.descriptorFactory = descriptorFactory;
    }


    @Override
    public boolean supported(MessageDescriptor<?> descriptor) {
        return descriptor.getMessageType() == MessageType.ORDER;
    }

    @Override
    public Object unmarshal(Reader reader, MessageDescriptor<?> descriptor) {
        Object instance = ReflectUtil.newInstance(descriptor.getJavaType());

        List<FieldDescriptor> fieldDescriptors = descriptor.getFieldDescriptors();
        for (int i = 0; i < fieldDescriptors.size(); i++) {
            BasicFieldDescriptor fd = (BasicFieldDescriptor) fieldDescriptors.get(i);
            int size = fd.getSize();
            if (size == 0 && i == fieldDescriptors.size() - 1) {
                size = reader.readableBytes();
            }
            try {
                Object value = fieldExtractor.unmarshal(reader.readBytes(size), fd);
                ReflectUtil.invoke(instance, fd.getWriteMethod(), value);
            } catch (Exception e) {
                String message = String.format("[unmarshal] id is %x, for %s.%s; nest message is %s",
                        descriptor.getId(),
                        descriptor.getJavaType().getName(),
                        fd.getName(),
                        e.getMessage());
                log.error(message);
                throw new ExtractException(message);
            }
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
        for (FieldDescriptor fd : descriptor.getFieldDescriptors()) {
            Object value = safeValue(ReflectUtil.invoke(bean, fd.getReadMethod()), fd.getFieldType());
            if (value == null) {
                log.warn("fixed message's field cannot be null, {}.{}", descriptor.getJavaType().getName(), fd.getName());
                continue;
            }
            try {
                fieldExtractor.marshal(writer, fd, value);
            } catch (Exception e) {
                log.error("[encode] id is {}, field is {}; nest message is {}",
                        Integer.toHexString(descriptor.getId()), fd.getName(), e.getMessage());
            }
        }
    }

    private Object safeValue(Object value, Class<?> type) {
        if (value != null) {
            return value;
        }
        if (type == Integer.class || type == Byte.class || type == Short.class) {
            return 0;
        }
        if (type == Long.class) {
            return 0L;
        }
        if (type == Double.class || type == Float.class) {
            return 0.0;
        }
        if (type == String.class) {
            return "";
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void marshal(Writer writer, T bean) {
        marshal(writer, descriptorFactory.getMessageDescriptor((Class<T>) bean.getClass()), bean);
    }
}
