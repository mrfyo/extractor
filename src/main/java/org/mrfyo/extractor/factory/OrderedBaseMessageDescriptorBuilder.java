package org.mrfyo.extractor.factory;

import org.mrfyo.extractor.annotation.OrderField;
import org.mrfyo.extractor.annotation.OrderMessage;
import org.mrfyo.extractor.bean.OrderedFieldDescriptor;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.bean.MessageDescriptor;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Feng Yong
 */
public class OrderedBaseMessageDescriptorBuilder extends BaseMessageDescriptorBuilder {

    @Override
    boolean supported(Class<?> messageType) {
        return messageType.isAnnotationPresent(OrderMessage.class);
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
        List<FieldDescriptor> fds = new ArrayList<>(fieldDescriptors);
        return create(messageType, fds);
    }

    private  <T> MessageDescriptor<T> create(Class<T> messageType, List<FieldDescriptor> fds) {
        OrderMessage message = messageType.getAnnotation(OrderMessage.class);
        String type = OrderMessage.class.getSimpleName();
        return new MessageDescriptor<>(message.id(), type, message.desc(), messageType, fds);
    }
}
