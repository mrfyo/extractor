package org.mrfyo.extractor.type;

import cn.hutool.core.convert.Convert;
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
        WriterUtil.writeInt(writer, value, descriptor.getDataType());
    }

    @Override
    public Short unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return Convert.convert(Short.class, ReaderUtil.readLong(reader, descriptor.getDataType()));
    }
}
