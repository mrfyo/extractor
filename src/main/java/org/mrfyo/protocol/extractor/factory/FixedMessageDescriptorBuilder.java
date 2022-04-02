package org.mrfyo.protocol.extractor.factory;

import org.mrfyo.protocol.extractor.annotation.FixedField;
import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.bean.BasicFieldDescriptor;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.bean.MessageDescriptor;

import org.mrfyo.protocol.extractor.enums.JavaDataType;
import org.mrfyo.protocol.extractor.enums.MessageType;
import org.mrfyo.protocol.extractor.enums.RawDataType;
import org.mrfyo.protocol.extractor.util.FieldUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Feng Yong
 */
public class FixedMessageDescriptorBuilder extends MessageDescriptorBuilder {

    @Override
    boolean supported(Class<?> messageType) {
        Message annotation = messageType.getAnnotation(Message.class);
        return annotation != null && annotation.type().equals(MessageType.FIX);
    }

    @Override
    public <T> MessageDescriptor<T> build(Class<T> messageType) throws DescriptorBuilderException {
        List<Field> fields = FieldUtils.getDeclaredFields(messageType, true,
                field -> field.isAnnotationPresent(FixedField.class));

        List<BasicFieldDescriptor> fieldDescriptors = new ArrayList<>(fields.size());
        for (Field field : fields) {
            PropertyDescriptor pd = getPropertyDescriptor(field.getName(), messageType);
            fieldDescriptors.add(new BasicFieldDescriptor(field, pd));
        }
        // sort by order.
        fieldDescriptors.sort(Comparator.comparing(BasicFieldDescriptor::getOrder));

        // check field.
        for (BasicFieldDescriptor fd : fieldDescriptors) {
            int size = fd.getSize();
            RawDataType rawType = fd.getRawType();
            if (size < 0) {
                throw new DescriptorBuilderException(fd.getName(), "fixed filed: size cannot less than 0");
            }
            JavaDataType javaType = fd.getJavaType();
            if (javaType.equals(JavaDataType.ANY)) {
                continue;
            }
            if (!typeMatches(rawType, javaType)) {
                throw new DescriptorBuilderException(fd.getName(), "raw type and java type mismatch");
            }
        }
        MessageDescriptor<T> messageDescriptor = MessageDescriptor.create(messageType);
        List<FieldDescriptor> fds = new ArrayList<>(fieldDescriptors);
        messageDescriptor.setFieldDescriptors(fds);
        return messageDescriptor;
    }


}
