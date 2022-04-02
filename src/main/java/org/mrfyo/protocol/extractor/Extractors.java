package org.mrfyo.protocol.extractor;

import org.mrfyo.protocol.extractor.bean.MessageDescriptor;
import org.mrfyo.protocol.extractor.io.ByteBufHelper;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;
import org.mrfyo.protocol.extractor.message.MessageExtractor;
import org.mrfyo.protocol.extractor.message.MessageExtractorAggregator;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Feng Yong
 */
public class Extractors {

    private static MessageExtractor extractor = new MessageExtractorAggregator();

    private static Charset charset = StandardCharsets.UTF_8;

    public static void setExtractor(MessageExtractor extractor) {
        Extractors.extractor = extractor;
    }

    public static void setCharset(Charset charset) {
        Extractors.charset = charset;
    }

    public static Charset getCharset() {
        return charset;
    }

    /**
     * 编组实体类，写入到 {@link Writer}
     *
     * @param writer {@link Writer}
     * @param bean   消息实体类对象
     */

    public static <T> void marshal(Writer writer, T bean) {
        extractor.marshal(writer, bean);
    }

    /**
     * 编组实体类，写入到 {@link Writer}
     *
     * @param writer     {@link Writer}
     * @param descriptor {@link MessageDescriptor}
     * @param bean       消息实体类对象
     */
    public static void marshal(Writer writer, MessageDescriptor<?> descriptor, Object bean) {
        extractor.marshal(writer, descriptor, bean);
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
        extractor.marshal(writer, message);
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
        return extractor.unmarshal(reader, clazz);
    }

    /**
     * 解组字节序列，将 {@link Reader}中数据注入到指定 OOP里面
     *
     * @param reader     {@link Reader}
     * @param descriptor {@link MessageDescriptor}
     * @return message Object.
     */
    public static Object unmarshal(Reader reader, MessageDescriptor<?> descriptor) {
        return extractor.unmarshal(reader, descriptor);
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
        return extractor.unmarshal(ByteBufHelper.copiedBuffer(bytes), type);
    }

    private Extractors() {
    }

}
