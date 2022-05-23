package org.mrfyo.protocol.extractor.factory;

import org.mrfyo.protocol.extractor.annotation.ExtraField;
import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.bean.ExtraFieldDescriptor;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.bean.MessageDescriptor;
import org.mrfyo.protocol.extractor.MessageType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Feng Yong
 */
public class ExtraMessageDescriptorBuilder extends MessageDescriptorBuilder {

    @Override
    boolean supported(Class<?> messageType) {
        Message annotation = messageType.getAnnotation(Message.class);
        return annotation != null && annotation.type() == MessageType.EXTRA;
    }

    @Override
    public <T> MessageDescriptor<T> build(Class<T> messageType) throws DescriptorBuilderException {
        List<Field> fields = getDeclaredFields(messageType, true,
                field -> field.isAnnotationPresent(ExtraField.class));
        List<ExtraFieldDescriptor> fieldDescriptors = new ArrayList<>(fields.size());

        for (Field field : fields) {
            PropertyDescriptor pd = getPropertyDescriptor(field.getName(), messageType);
            fieldDescriptors.add(new ExtraFieldDescriptor(field, pd));
        }

        MessageDescriptor<T> messageDescriptor = MessageDescriptor.create(messageType);
        List<FieldDescriptor> fds = new ArrayList<>(fieldDescriptors);
        messageDescriptor.setFieldDescriptors(fds);
        return messageDescriptor;
    }
}
