package org.mrfyo.protocol.extractor.factory;


import org.mrfyo.protocol.extractor.bean.MessageDescriptor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;


/**
 * @author Feng Yong
 */
public abstract class MessageDescriptorBuilder {


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
}
