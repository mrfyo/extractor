package org.mrfyo.protocol.extractor.type;

import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.Extractors;
import org.mrfyo.protocol.extractor.annotation.ListField;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;

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
        ListField listField = descriptor.getAnnotation(ListField.class);
        Class<?> itemType = listField.itemType();


        List<Object> list = new ArrayList<>();
        while (reader.readableBytes() > 0) {
            list.add(Extractors.unmarshal(reader, itemType));
        }
        return list;
    }
}
