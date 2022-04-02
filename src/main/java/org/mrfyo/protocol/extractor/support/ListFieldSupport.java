package org.mrfyo.protocol.extractor.support;

import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.Extractors;
import org.mrfyo.protocol.extractor.annotation.ListField;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.field.FieldExtractor;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Feng Yong
 */
public class ListFieldSupport implements FieldExtractor<List<Object>> {
    @Override
    public List<Object> unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        ListField listField = descriptor.getAnnotation(ListField.class);
        int size = listField.itemSize();
        Class<?> itemType = listField.itemType();

        List<Object> list = new ArrayList<>();
        while (reader.isReadable(size)) {
            list.add(Extractors.unmarshal(reader.readBytes(size), itemType));
        }
        return list;
    }

    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Object value) throws ExtractException {
        if (value instanceof List list) {
            for (Object item : list) {
                Extractors.marshal(writer, item);
            }
        }
    }
}
