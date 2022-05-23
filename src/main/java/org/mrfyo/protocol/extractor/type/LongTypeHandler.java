package org.mrfyo.protocol.extractor.type;

import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.enums.DataType;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class LongTypeHandler implements TypeHandler<Long> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Long value) throws ExtractException {
        DataType dataType = descriptor.getDataType();
        if (dataType.equals(DataType.BYTE)) {
            writer.writeUint8(Math.toIntExact(value));
        } else if (dataType.equals(DataType.WORD)) {
            writer.writeUint16(Math.toIntExact(value));
        } else if (dataType.equals(DataType.DWORD)) {
            writer.writeUint32(Math.toIntExact(value));
        } else {
            throw new ExtractException("cannot marshal " + dataType);
        }

    }

    @Override
    public Long unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        DataType dataType = descriptor.getDataType();
        if (dataType.equals(DataType.BYTE)) {
            return (long) reader.readUint8();
        } else if (dataType.equals(DataType.WORD)) {
            return (long) reader.readUint16();
        } else if (dataType.equals(DataType.DWORD)) {
            return reader.readUint32();
        } else {
            throw new ExtractException("cannot unmarshal " + dataType);
        }
    }
}
