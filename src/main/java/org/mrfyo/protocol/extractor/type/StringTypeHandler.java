package org.mrfyo.protocol.extractor.type;

import cn.hutool.core.util.HexUtil;
import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;


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
