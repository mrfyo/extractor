package org.mrfyo.protocol.extractor.factory;

import org.mrfyo.protocol.extractor.annotation.OrderField;
import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.bean.BasicFieldDescriptor;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.bean.MessageDescriptor;

import org.mrfyo.protocol.extractor.enums.InternalMessageType;
import org.mrfyo.protocol.extractor.util.FieldUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Feng Yong
 */
public class OrderedMessageDescriptorBuilder extends MessageDescriptorBuilder {

    @Override
    boolean supported(Class<?> messageType) {
        Message annotation = messageType.getAnnotation(Message.class);
        return annotation != null && annotation.type().equals(InternalMessageType.ORDER);
    }

    @Override
    public <T> MessageDescriptor<T> build(Class<T> messageType) throws DescriptorBuilderException {
        List<Field> fields = FieldUtils.getDeclaredFields(messageType, true,
                field -> field.isAnnotationPresent(OrderField.class));

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
            if (size < 0) {
                throw new DescriptorBuilderException(fd.getName(), "fixed filed: size cannot less than 0");
            }
        }
        MessageDescriptor<T> messageDescriptor = MessageDescriptor.create(messageType);
        List<FieldDescriptor> fds = new ArrayList<>(fieldDescriptors);
        messageDescriptor.setFieldDescriptors(fds);
        return messageDescriptor;
    }


}