package org.mrfyo.extractor.type;

import cn.hutool.core.util.HexUtil;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Feng Yong
 */
public class StringTypeHandler implements TypeHandler<String> {

    private final static Logger log = LoggerFactory.getLogger(StringTypeHandler.class);

    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, String value) throws ExtractException {
        if (value.isEmpty()) {
            int size = descriptor.getSize();
            if (size == 0) {
                return;
            }
            if (size > 0) {
                value = "0".repeat(size * 2);
            }
        }
        try {
            writer.writeBytes(HexUtil.decodeHex(value));
        } catch (Exception e) {
            throw new TypeHandleException("StringTypeHandler cannot handle value: " + value);
        }

    }

    @Override
    public String unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        byte[] bytes = new byte[reader.readableBytes()];
        reader.readBytes(bytes);
        int size = descriptor.getSize();
        if(size > 0 && size != bytes.length) {
            log.warn("byte size: expected {} but actual {}", size, bytes.length);
        }

        return HexUtil.encodeHexStr(bytes);
    }
}
