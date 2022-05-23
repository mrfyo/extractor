package org.mrfyo.extractor.type;


import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;

/**
 * 字段解析器
 *
 * @author Feyon
 * @date 2021/8/4
 */
public interface TypeHandler<T> {

    /**
     * 编组字节序列，写入{@link Writer}
     *
     * @param writer     {@link Writer}
     * @param descriptor 字段描述器
     * @param value      非空字段值
     * @throws ExtractException 编组异常
     */
    void marshal(Writer writer, FieldDescriptor descriptor, T value) throws ExtractException;


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
