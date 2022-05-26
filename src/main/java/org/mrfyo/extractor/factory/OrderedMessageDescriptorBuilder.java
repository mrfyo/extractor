package org.mrfyo.extractor.factory;

import org.mrfyo.extractor.annotation.Message;
import org.mrfyo.extractor.MessageType;
import org.mrfyo.extractor.annotation.OrderField;
import org.mrfyo.extractor.bean.OrderedFieldDescriptor;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.bean.MessageDescriptor;


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
        return annotation != null && annotation.type() == MessageType.ORDER;
    }

    @Override
    public <T> MessageDescriptor<T> build(Class<T> messageType) throws DescriptorBuilderException {
        List<Field> fields = getDeclaredFields(messageType, true,
                field -> field.isAnnotationPresent(OrderField.class));

        List<OrderedFieldDescriptor> fieldDescriptors = new ArrayList<>(fields.size());
        for (Field field : fields) {
            PropertyDescriptor pd = getPropertyDescriptor(field.getName(), messageType);
            fieldDescriptors.add(new OrderedFieldDescriptor(field, pd));
        }
        // sort by order.
        fieldDescriptors.sort(Comparator.comparing(OrderedFieldDescriptor::getOrder));

        // check field.
        for (OrderedFieldDescriptor fd : fieldDescriptors) {
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
