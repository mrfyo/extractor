package org.mrfyo.extractor.factory;

import org.mrfyo.extractor.annotation.BitField;
import org.mrfyo.extractor.annotation.BitMessage;
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
public class BitBaseMessageDescriptorBuilder extends BaseMessageDescriptorBuilder {

    @Override
    boolean supported(Class<?> messageType) {
        return messageType.isAnnotationPresent(BitMessage.class);
    }

    @Override
    public <T> MessageDescriptor<T> build(Class<T> messageType) throws DescriptorBuilderException {
        List<Field> fields = getDeclaredFields(messageType, false,
                field -> field.isAnnotationPresent(BitField.class));

        List<BitFieldDescriptor> fieldDescriptors = new ArrayList<>(fields.size());
        for (Field field : fields) {
            PropertyDescriptor pd = getPropertyDescriptor(field.getName(), messageType);
            fieldDescriptors.add(new BitFieldDescriptor(field, pd));
        }
        fieldDescriptors.sort(Comparator.comparing(BitFieldDescriptor::getIndex));
        List<FieldDescriptor> fds = new ArrayList<>(fieldDescriptors);
        return create(messageType, fds);
    }

    private  <T> MessageDescriptor<T> create(Class<T> messageType, List<FieldDescriptor> fds) {
        String type = BitMessage.class.getSimpleName();
        return new MessageDescriptor<>(0, type, "", messageType, fds);
    }
}
