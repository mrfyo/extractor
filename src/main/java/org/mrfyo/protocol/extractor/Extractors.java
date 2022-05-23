package org.mrfyo.protocol.extractor;

import org.mrfyo.protocol.extractor.bean.MessageDescriptor;
import org.mrfyo.protocol.extractor.io.ByteBufHelper;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;
import org.mrfyo.protocol.extractor.extractor.MessageExtractorAggregator;


/**
 * @author Feng Yong
 */
public class Extractors {

    private volatile static MessageExtractorAggregator extractor;

    private Extractors() {
    }

    public static MessageExtractorAggregator getExtractor() {
        if (extractor == null) {
            synchronized (Extractors.class) {
                if (extractor == null) {
                    extractor = new MessageExtractorAggregator();
                }
            }
        }
        return extractor;
    }

    public static void setExtractor(MessageExtractorAggregator extractor) {
        Extractors.extractor = extractor;
    }

    /**
     * 编组实体类，写入到 {@link Writer}
     *
     * @param writer {@link Writer}
     * @param bean   消息实体类对象
     */

    public static <T> void marshal(Writer writer, T bean) {
        getExtractor().marshal(writer, bean);
    }

    /**
     * 编组实体类，写入到 {@link Writer}
     *
     * @param writer     {@link Writer}
     * @param descriptor {@link MessageDescriptor}
     * @param bean       消息实体类对象
     */
    public static void marshal(Writer writer, MessageDescriptor<?> descriptor, Object bean) {
        getExtractor().marshal(writer, descriptor, bean);
    }

    /**
     * 默认编组快捷方式
     *
     * @param message 消息实体类
     * @param cap     估计大小
     * @return 字节缓冲器
     */
    public static <T> Writer marshal(T message, int cap) {
        Writer writer = ByteBufHelper.buffer(cap);
        getExtractor().marshal(writer, message);
        return writer;
    }

    /**
     * 解组字节序列，将 {@link Reader}中数据注入到指定 OOP里面
     *
     * @param reader {@link Reader}
     * @param clazz  消息实体类
     * @return message Object.
     */
    public static <T> T unmarshal(Reader reader, Class<T> clazz) {
        return getExtractor().unmarshal(reader, clazz);
    }

    /**
     * 解组字节序列，将 {@link Reader}中数据注入到指定 OOP里面
     *
     * @param reader     {@link Reader}
     * @param descriptor {@link MessageDescriptor}
     * @return message Object.
     */
    public static Object unmarshal(Reader reader, MessageDescriptor<?> descriptor) {
        return getExtractor().unmarshal(reader, descriptor);
    }

    /**
     * 解组快捷方式，支持字节数组注入
     *
     * @param bytes 消息字节序列
     * @param type  类型
     * @param <T>   完整的消息类型
     * @return 消息实体类
     */
    public static <T> T unmarshal(byte[] bytes, Class<T> type) {
        return getExtractor().unmarshal(ByteBufHelper.copiedBuffer(bytes), type);
    }



}
