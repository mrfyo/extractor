package org.mrfyo.protocol.extractor.factory;


import org.mrfyo.protocol.extractor.bean.MessageDescriptor;
import org.mrfyo.protocol.extractor.enums.JavaDataType;
import org.mrfyo.protocol.extractor.enums.RawDataType;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mrfyo.protocol.extractor.enums.JavaDataType.*;
import static org.mrfyo.protocol.extractor.enums.RawDataType.*;
import static org.mrfyo.protocol.extractor.enums.RawDataType.BYTES;

/**
 * @author Feng Yong
 */
public abstract class MessageDescriptorBuilder {

    protected static final Map<RawDataType, List<JavaDataType>> SUPPORT_MAP = new HashMap<>(16);

    static {
        SUPPORT_MAP.put(BYTE, List.of(INT, DOUBLE, ANY));
        SUPPORT_MAP.put(WORD, List.of(ANY, INT, DOUBLE));
        SUPPORT_MAP.put(DWORD, List.of(ANY, INT, DOUBLE));
        SUPPORT_MAP.put(BYTES, List.of(ANY, STRING));
    }

    /**
     * return is or not support building.
     *
     * @param messageType message class type
     * @return is or not support building
     */
    abstract boolean supported(Class<?> messageType);

    /**
     * build message descriptor.
     *
     * @param messageType message class type
     * @param <T>         message type
     * @return message descriptor
     * @throws DescriptorBuilderException if build fail.
     */
    abstract <T> MessageDescriptor<T> build(Class<T> messageType) throws DescriptorBuilderException;


    protected PropertyDescriptor getPropertyDescriptor(String fieldName, Class<?> type) throws DescriptorBuilderException {
        try {
            return new PropertyDescriptor(fieldName, type);
        } catch (IntrospectionException e) {
            throw new DescriptorBuilderException(fieldName, "field cannot visit");
        }
    }

    protected boolean typeMatches(RawDataType rawDataType, JavaDataType javaDataType) {
        return SUPPORT_MAP.containsKey(rawDataType) &&
                SUPPORT_MAP.get(rawDataType).contains(javaDataType);
    }
}
