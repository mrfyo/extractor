package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.Extractors;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class ObjectTypeHandler implements TypeHandler<Object> {

    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Object value) throws ExtractException {
        Extractors.marshal(writer, value);
    }

    @Override
    public Object unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return Extractors.unmarshal(reader, descriptor.getFieldType());
    }
}
