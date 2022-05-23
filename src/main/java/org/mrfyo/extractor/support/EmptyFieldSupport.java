package org.mrfyo.extractor.support;


import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.type.TypeHandler;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;


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
