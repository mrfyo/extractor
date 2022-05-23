package org.mrfyo.extractor.extractor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.MessageType;
import org.mrfyo.extractor.bean.BitFieldDescriptor;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.bean.MessageDescriptor;
import org.mrfyo.extractor.factory.MessageDescriptorFactory;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Feng Yong
 */
public class BitMessageExtractor implements MessageExtractor {
    private final Logger log = LoggerFactory.getLogger(BitMessageExtractor.class);

    private final MessageDescriptorFactory descriptorFactory;

    public BitMessageExtractor(MessageDescriptorFactory descriptorFactory) {
        this.descriptorFactory = descriptorFactory;
    }

    @Override
    public boolean supported(MessageDescriptor<?> descriptor) {
        return descriptor.getMessageType() == MessageType.BIT;
    }

    @Override
    public Object unmarshal(Reader reader, MessageDescriptor<?> descriptor) {
        long b;
        int n = reader.readableBytes();
        if (n == 1) {
            b = reader.readUint8();
        } else if (n == 2) {
            b = reader.readUint16();
        } else if (n == 4) {
            b = reader.readUint32();
        } else {
            throw new ExtractException("unsupported byte length: " + n);
        }
        Object instance = ReflectUtil.newInstance(descriptor.getJavaType());
        for (FieldDescriptor fd : descriptor.getFieldDescriptors()) {
            BitFieldDescriptor bfd = (BitFieldDescriptor) fd;
            Class<?> fieldType = fd.getFieldType();
            try {
                int index = bfd.getIndex();
                long v = (b >> index) & 0x01;
                 fd.getWriteMethod().invoke(instance, Convert.convert(fieldType, v));

            } catch (Exception e) {
                String message = String.format("[unmarshal] id is %x, for %s.%s; nest message is %s",
                        descriptor.getId(),
                        descriptor.getJavaType().getName(),
                        fd.getName(),
                        e.getMessage());
                log.error(message);
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
        int b = 0;
        int maxIndex = 0;
        for (FieldDescriptor fd : descriptor.getFieldDescriptors()) {
            int v = Convert.convert(int.class, ReflectUtil.invoke(bean, fd.getReadMethod(), fd.getFieldType()));
            int bit = v > 0 ? 1 : 0;
            int index = ((BitFieldDescriptor) fd).getIndex();
            b |= (bit << index);
            maxIndex = Math.max(maxIndex, index);
        }
        if (maxIndex < 8) {
            writer.writeUint8(b);
        } else if (maxIndex < 16) {
            writer.writeUint16(b);
        } else if (b == 4) {
            writer.writeUint32(b);
        } else {
            throw new ExtractException("unsupported max index: " + maxIndex);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void marshal(Writer writer, T bean) {
        marshal(writer, descriptorFactory.getMessageDescriptor((Class<T>) bean.getClass()), bean);
    }


}
