package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class ByteObjectArrayTypeHandler implements TypeHandler<Byte[]> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Byte[] value) throws ExtractException {
        writer.writeBytes(convertToPrimitiveArray(value));
    }

    @Override
    public Byte[] unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        byte[] b = new byte[reader.readableBytes()];
        reader.readBytes(b);
        return convertToObjectArray(b);
    }


    public static byte[] convertToPrimitiveArray(Byte[] objects) {
        final byte[] bytes = new byte[objects.length];
        for (int i = 0; i < objects.length; i++) {
            bytes[i] = objects[i];
        }
        return bytes;
    }

    public static Byte[] convertToObjectArray(byte[] bytes) {
        final Byte[] objects = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            objects[i] = bytes[i];
        }
        return objects;
    }
}
