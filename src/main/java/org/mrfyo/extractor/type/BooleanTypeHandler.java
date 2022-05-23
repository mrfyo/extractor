package org.mrfyo.extractor.type;

import cn.hutool.core.convert.Convert;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class BooleanTypeHandler implements TypeHandler<Boolean> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Boolean v) throws ExtractException {
        int value = v ? 1 : 0;
        DataType dataType = descriptor.getDataType();
        if (dataType.equals(DataType.BYTE)) {
            writer.writeUint8(value);
        } else if (dataType.equals(DataType.WORD)) {
            writer.writeUint16(value);
        } else if (dataType.equals(DataType.DWORD)) {
            writer.writeUint32(value);
        } else {
            throw new ExtractException("cannot marshal " + dataType + " to byte");
        }
    }

    @Override
    public Boolean unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        DataType dataType = descriptor.getDataType();
        if (dataType.equals(DataType.BYTE)) {
            return Convert.convert(Boolean.class, reader.readUint8());
        } else if (dataType.equals(DataType.WORD)) {
            return Convert.convert(Boolean.class, reader.readUint16());
        } else if (dataType.equals(DataType.DWORD)) {
            return Convert.convert(Boolean.class, reader.readUint32());
        } else {
            throw new ExtractException("cannot unmarshal " + dataType);
        }
    }
}
