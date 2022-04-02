package org.mrfyo.protocol.extractor.support;

import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.Extractors;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.field.FieldExtractor;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;


/**
 * @author Feng Yong
 */
public class StringFieldSupport implements FieldExtractor<String> {
    @Override
    public String unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        byte[] b = new byte[reader.readableBytes()];
        reader.readBytes(b);
        return new String(b, Extractors.getCharset());
    }

    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Object value) throws ExtractException {
        String s = (String) value;
        writer.writeBytes(s.getBytes(Extractors.getCharset()));
    }
}
