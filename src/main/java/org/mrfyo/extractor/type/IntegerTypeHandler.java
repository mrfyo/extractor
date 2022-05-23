package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class IntegerTypeHandler implements TypeHandler<Integer> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Integer value) throws ExtractException {
        DataType dataType = descriptor.getDataType();
        if(dataType.equals(DataType.BYTE)) {
            writer.writeUint8(value);
        }else if (dataType.equals(DataType.WORD)) {
            writer.writeUint16(value);
        }else if (dataType.equals(DataType.DWORD)) {
            writer.writeUint32(value);
        }else {
            throw new ExtractException("cannot marshal " + dataType);
        }

    }

    @Override
    public Integer unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        DataType dataType = descriptor.getDataType();
        if(dataType.equals(DataType.BYTE)) {
            return reader.readUint8();
        }else if (dataType.equals(DataType.WORD)) {
            return reader.readUint16();
        }else if (dataType.equals(DataType.DWORD)) {
            return Math.toIntExact(reader.readUint32());
        }else {
            throw new ExtractException("cannot unmarshal " + dataType);
        }
    }
}
