package org.mrfyo.extractor.factory;

import org.mrfyo.extractor.bean.MessageDescriptor;

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
     * @return no-null message descriptor
     * @throws DescriptorBuilderException if message type build error.
     */
    <T> MessageDescriptor<T> getMessageDescriptor(Class<T> messageType);
}
