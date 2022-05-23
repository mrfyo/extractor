package org.mrfyo.extractor.type;

import cn.hutool.core.util.HexUtil;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;


/**
 * @author Feng Yong
 */
public class StringTypeHandler implements TypeHandler<String> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, String value) throws ExtractException {
        writer.writeBytes(HexUtil.decodeHex(value));
    }

    @Override
    public String unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        byte[] bytes = new byte[reader.readableBytes()];
        reader.readBytes(bytes);
        return HexUtil.encodeHexStr(bytes);
    }
}
