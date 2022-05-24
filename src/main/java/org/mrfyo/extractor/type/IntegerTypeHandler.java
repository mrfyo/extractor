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
public class IntegerTypeHandler implements TypeHandler<Integer> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Integer value) throws ExtractException {
        WriterUtil.writeInt(writer, value, descriptor.getDataType());
    }

    @Override
    public Integer unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return ReaderUtil.readInt(reader, descriptor.getDataType());
    }
}
