package org.mrfyo.extractor.factory;

import org.mrfyo.extractor.MessageType;
import org.mrfyo.extractor.annotation.BitField;
import org.mrfyo.extractor.annotation.Message;
import org.mrfyo.extractor.bean.BitFieldDescriptor;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.bean.MessageDescriptor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Feng Yong
 */
public class BitMessageDescriptorBuilder extends MessageDescriptorBuilder {

    @Override
    boolean supported(Class<?> messageType) {
        Message annotation = messageType.getAnnotation(Message.class);
        return annotation != null && annotation.type() == MessageType.BIT;
    }

    @Override
    public <T> MessageDescriptor<T> build(Class<T> messageType) throws DescriptorBuilderException {
        List<Field> fields = getDeclaredFields(messageType, false,
                field -> field.isAnnotationPresent(BitField.class));

        List<BitFieldDescriptor> fieldDescriptors = new ArrayList<>(fields.size());
        for (Field field : fields) {
            PropertyDescriptor pd = getPropertyDescriptor(field.getName(), messageType);;
            fieldDescriptors.add(new BitFieldDescriptor(field, pd));
        }
        fieldDescriptors.sort(Comparator.comparing(BitFieldDescriptor::getIndex));

        MessageDescriptor<T> messageDescriptor = MessageDescriptor.create(messageType);
        List<FieldDescriptor> fds = new ArrayList<>(fieldDescriptors);
        messageDescriptor.setFieldDescriptors(fds);
        return messageDescriptor;
    }


}
