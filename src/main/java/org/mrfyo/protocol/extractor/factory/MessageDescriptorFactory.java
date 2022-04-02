package org.mrfyo.protocol.extractor.factory;

import org.mrfyo.protocol.extractor.bean.MessageDescriptor;

/**
 * Message Descriptor Factory
 *
 * @author Feng Yong
 */
public interface MessageDescriptorFactory {


    /**
     * return message descriptor for message type
     *
     * @param messageType message class type
     * @param <T>         message type
     * @return message descriptor
     */
    <T> MessageDescriptor<T> getMessageDescriptor(Class<T> messageType);
}
