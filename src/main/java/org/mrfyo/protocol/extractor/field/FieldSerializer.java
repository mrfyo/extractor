package org.mrfyo.protocol.extractor.field;


import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.io.Writer;

/**
 * @author Feyon
 * @date 2021/8/11
 */
public interface FieldSerializer {

    /**
     * 编组字节序列，写入{@link Writer}
     * @param writer {@link Writer}
     * @param descriptor 字段描述器
     * @param value 字段值
     * @throws ExtractException 编码错误移除
     */
    void marshal(Writer writer, FieldDescriptor descriptor, Object value) throws ExtractException;
}
