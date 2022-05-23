package org.mrfyo.protocol.extractor.type;

import cn.hutool.core.convert.Convert;
import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.enums.DataType;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class ShortTypeHandler implements TypeHandler<Short> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Short value) throws ExtractException {
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
    public Short unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        DataType dataType = descriptor.getDataType();
        if(dataType.equals(DataType.BYTE)) {
            return Convert.convert(Short.class, reader.readUint8());
        }else if (dataType.equals(DataType.WORD)) {
            return Convert.convert(Short.class, reader.readUint16());
        }else if (dataType.equals(DataType.DWORD)) {
            return Convert.convert(Short.class, reader.readUint32());
        }else {
            throw new ExtractException("cannot unmarshal " + dataType);
        }
    }
}
