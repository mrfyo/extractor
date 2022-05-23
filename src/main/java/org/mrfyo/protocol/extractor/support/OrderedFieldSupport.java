package org.mrfyo.protocol.extractor.support;

import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.Extractors;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.type.TypeHandler;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class OrderedFieldSupport implements TypeHandler<Object> {

    @Override
    public Object unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return Extractors.unmarshal(reader, descriptor.getFieldType());
    }

    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Object value) throws ExtractException {
        Extractors.marshal(writer, value);
    }

}
