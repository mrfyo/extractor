package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class ByteArrayTypeHandler implements TypeHandler<byte[]> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, byte[] value) throws ExtractException {
        writer.writeBytes(value);
    }

    @Override
    public byte[] unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        byte[] b = new byte[reader.readableBytes()];
        reader.readBytes(b);
        return b;
    }
}
