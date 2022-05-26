package org.mrfyo.extractor.factory;

import org.mrfyo.extractor.annotation.ExtraField;
import org.mrfyo.extractor.annotation.ExtraMessage;
import org.mrfyo.extractor.bean.ExtraFieldDescriptor;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.bean.MessageDescriptor;
import org.mrfyo.extractor.enums.DataType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Feng Yong
 */
public class ExtraBaseMessageDescriptorBuilder extends BaseMessageDescriptorBuilder {

    @Override
    boolean supported(Class<?> messageType) {
        return messageType.isAnnotationPresent(ExtraMessage.class);
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

        List<FieldDescriptor> fds = new ArrayList<>(fieldDescriptors);
        return create(messageType, fds);
    }

    private <T> MessageDescriptor<T> create(Class<T> messageType, List<FieldDescriptor> fds) {
        ExtraMessage message = messageType.getAnnotation(ExtraMessage.class);
        String type = ExtraMessage.class.getSimpleName();
        DataType keyDataType = message.keyDataType();
        if(!(DataType.BYTE.equals(keyDataType) || DataType.WORD.equals(keyDataType))) {
            throw new DescriptorBuilderException(messageType.getName(), "keyDataType cannot support " + keyDataType);
        }
        DataType lengthDataType = message.lengthDataType();
        if(!(DataType.BYTE.equals(lengthDataType) || DataType.WORD.equals(lengthDataType))) {
            throw new DescriptorBuilderException(messageType.getName(), "keyDataType cannot support " + lengthDataType);
        }
        return new MessageDescriptor<>(message.id(), type, message.desc(), messageType , fds);
    }
}
