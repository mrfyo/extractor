package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.mrfyo.extractor.util.ReaderUtil;
import org.mrfyo.extractor.util.WriterUtil;

/**
 * @author Feng Yong
 */
public class ShortTypeHandler implements TypeHandler<Short> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Short value) throws ExtractException {
        DataType dataType = descriptor.getDataType();
        if (DataType.BYTE.equals(dataType) || DataType.WORD.equals(dataType)) {
            WriterUtil.writeInt(writer, value, dataType);
        } else {
            throw new TypeHandleException("dataType is" + dataType + " but javaType is " + descriptor.getFieldType());
        }
    }

    @Override
    public Short unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        DataType dataType = descriptor.getDataType();
        if (DataType.BYTE.equals(dataType) || DataType.WORD.equals(dataType)) {
            return (short) ReaderUtil.readLong(reader, descriptor.getDataType());
        }
        throw new TypeHandleException("dataType is" + dataType + " but javaType is " + descriptor.getFieldType());
    }
}
