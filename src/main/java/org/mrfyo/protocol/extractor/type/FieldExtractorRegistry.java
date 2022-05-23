package org.mrfyo.protocol.extractor.type;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Feng Yong
 */
public final class FieldExtractorRegistry {

    private final Map<Class<?>, TypeHandler<?>> extractorMap = new ConcurrentHashMap<>();


    public FieldExtractorRegistry() {
        extractorMap.put(Byte.class, new ByteTypeHandler());
        extractorMap.put(byte.class, new ByteTypeHandler());

        extractorMap.put(Short.class, new ShortTypeHandler());
        extractorMap.put(short.class, new ShortTypeHandler());

        extractorMap.put(Integer.class, new IntegerTypeHandler());
        extractorMap.put(int.class, new IntegerTypeHandler());

        extractorMap.put(Long.class, new LongTypeHandler());
        extractorMap.put(long.class, new LongTypeHandler());

        extractorMap.put(Double.class, new DoubleTypeHandler());
        extractorMap.put(double.class, new DoubleTypeHandler());

        extractorMap.put(Byte[].class, new ByteObjectArrayTypeHandler());
        extractorMap.put(byte[].class, new ByteArrayTypeHandler());

        extractorMap.put(String.class, new StringTypeHandler());

        extractorMap.put(List.class, new ListTypeHandler());

    }


    public boolean hasExtractor(Class<?> type) {
        return extractorMap.containsKey(type);
    }

    @SuppressWarnings("unchecked")
    public <T> TypeHandler<T> getExtractor(Class<T> type) {
        return (TypeHandler<T>) extractorMap.get(type);
    }

    public void register(Class<?> type, TypeHandler<?> extractor) {
        extractorMap.put(type, extractor);
    }
}
