package org.mrfyo.extractor.type;

import cn.hutool.core.convert.Convert;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.mrfyo.extractor.util.ReaderUtil;
import org.mrfyo.extractor.util.WriterUtil;

/**
 * @author Feng Yong
 */
public class ByteTypeHandler implements TypeHandler<Byte> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Byte value) throws ExtractException {
        WriterUtil.writeInt(writer, value, descriptor.getDataType());
    }

    @Override
    public Byte unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return Convert.convert(Byte.class, ReaderUtil.readLong(reader, descriptor.getDataType()));
    }
}
