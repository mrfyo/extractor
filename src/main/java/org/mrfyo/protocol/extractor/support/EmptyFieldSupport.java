package org.mrfyo.protocol.extractor.support;


import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.field.FieldExtractor;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;


/**
 * 争对消息长度为 0 的附加子消息，当存在是即为 true
 * @author Feng Yong
 */
public class EmptyFieldSupport implements FieldExtractor<Boolean> {
    @Override
    public Boolean unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return true;
    }

    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Object value) throws ExtractException {

    }
}
