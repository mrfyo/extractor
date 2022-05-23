package org.mrfyo.extractor.factory;

import org.mrfyo.extractor.bean.MessageDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Feng Yong
 */
public class CacheMessageDescriptorFactory implements MessageDescriptorFactory {

    private final List<MessageDescriptorBuilder> builders = new ArrayList<>();

    private final Map<Class<?>, MessageDescriptor<?>> cache = new ConcurrentHashMap<>(16);

    private final Object syncObj = new Object();


    public CacheMessageDescriptorFactory() {
        addBuilder(new OrderedMessageDescriptorBuilder());
        addBuilder(new ExtraMessageDescriptorBuilder());
    }

    /**
     * add message descriptor builder
     *
     * @param builder message descriptor builder
     */
    public void addBuilder(MessageDescriptorBuilder builder) {
        builders.add(builder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> MessageDescriptor<T> getMessageDescriptor(Class<T> messageType) {
        if (cache.containsKey(messageType)) {
            return (MessageDescriptor<T>) cache.get(messageType);
        }
        synchronized (syncObj) {
            for (MessageDescriptorBuilder builder : builders) {
                if (builder.supported(messageType)) {
                    MessageDescriptor<T> messageDescriptor = builder.build(messageType);
                    cache.put(messageType, messageDescriptor);
                    return messageDescriptor;
                }
            }
        }
        throw new DescriptorBuilderException(messageType.getName(), "unsupported build this message type: " + messageType);
    }
}
