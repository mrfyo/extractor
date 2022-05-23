package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.Extractors;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Feng Yong
 */
public class ListTypeHandler implements TypeHandler<List<Object>> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, List<Object> list) throws ExtractException {
        for (Object item : list) {
            Extractors.marshal(writer, item);
        }
    }

    @Override
    public List<Object> unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        Class<?> itemType = getParameterizedType(descriptor.getField());
        List<Object> list = new ArrayList<>();
        while (reader.readableBytes() > 0) {
            list.add(Extractors.unmarshal(reader, itemType));
        }
        return list;
    }

    private Class<?> getParameterizedType(Field field) {
        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Type[] arguments = pt.getActualTypeArguments();
        return (Class<?>) arguments[0];
    }
}
