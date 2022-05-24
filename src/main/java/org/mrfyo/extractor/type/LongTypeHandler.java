package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.mrfyo.extractor.util.ReaderUtil;
import org.mrfyo.extractor.util.WriterUtil;

/**
 * @author Feng Yong
 */
public class LongTypeHandler implements TypeHandler<Long> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Long value) throws ExtractException {
        int v = value == null ? 0 : (int) value.longValue();
        WriterUtil.writeInt(writer, v, descriptor.getDataType());
    }

    @Override
    public Long unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return ReaderUtil.readLong(reader, descriptor.getDataType());
    }
}
