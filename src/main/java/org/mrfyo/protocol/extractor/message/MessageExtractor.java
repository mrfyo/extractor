package org.mrfyo.protocol.extractor.message;


import org.mrfyo.protocol.extractor.bean.MessageDescriptor;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;

/**
 * @author Feyon
 * @date 2021/8/2
 */
public interface MessageExtractor {

    /**
     * 返回是否支持处理该消息
     * @param descriptor {@link MessageDescriptor}
     * @return 如果支持处理，返回 true
     */
    boolean supported(MessageDescriptor<?> descriptor);

    /**
     * 解组字节序列，将 {@link Reader}中数据注入到指定 OOP里面
     * @param reader {@link Reader}
     * @param descriptor {@link MessageDescriptor}
     * @return message Object.
     */
     Object unmarshal(Reader reader, MessageDescriptor<?> descriptor);


    /**
     * 解组字节序列，将 {@link Reader}中数据注入到指定 OOP里面
     * @param reader {@link Reader}
     * @param clazz 消息实体类
     * @return message Object.
     */
    <T> T unmarshal(Reader reader, Class<T> clazz);

    /**
     * 编组实体类，写入到 {@link Writer}
     * @param writer {@link Writer}
     * @param descriptor {@link MessageDescriptor}
     * @param bean 消息实体类对象
     */
    void marshal(Writer writer, MessageDescriptor<?> descriptor, Object bean );

    /**
     * 编组实体类，写入到 {@link Writer}
     * @param writer {@link Writer}
     * @param bean 消息实体类对象
     */
    <T> void marshal(Writer writer, T bean);



}
