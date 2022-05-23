package org.mrfyo.protocol.extractor.support;


import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.type.TypeHandler;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;


/**
 * 争对消息长度为 0 的附加子消息，当存在是即为 true
 * @author Feng Yong
 */
public class EmptyFieldSupport implements TypeHandler<Boolean> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Boolean value) throws ExtractException {

    }

    @Override
    public Boolean unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return true;
    }

}
