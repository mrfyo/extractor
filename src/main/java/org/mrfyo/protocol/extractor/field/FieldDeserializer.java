package org.mrfyo.protocol.extractor.field;

import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.io.Reader;


/**
 * 字段反序列化器
 *
 * @author Feyon
 * @date 2021/8/3
 */
public interface FieldDeserializer<T> {

    /**
     * 将 bytes 反序列化为 OOP
     *
     * @param reader     {@link Reader}
     * @param descriptor {@link FieldDescriptor}
     * @return OOP
     * @throws ExtractException 编组异常
     */
    T unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException;

}
