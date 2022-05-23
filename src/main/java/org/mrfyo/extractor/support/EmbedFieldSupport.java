package org.mrfyo.extractor.support;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.Extractors;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.mrfyo.extractor.type.TypeHandler;

/**
 * @author Feng Yong
 */
public class EmbedFieldSupport implements TypeHandler<Object> {
    @Override
    public Object unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return Extractors.unmarshal(reader, descriptor.getFieldType());
    }

    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Object value) throws ExtractException {
        Extractors.marshal(writer, value);
    }
}
