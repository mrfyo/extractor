package org.mrfyo.extractor.factory;

import org.mrfyo.extractor.bean.MessageDescriptor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Feng Yong
 */
public class CacheMessageDescriptorFactory implements MessageDescriptorFactory {

    private final List<BaseMessageDescriptorBuilder> builders = new ArrayList<>();

    private final Map<Class<?>, MessageDescriptor<?>> cache = new ConcurrentHashMap<>(16);

    private final Set<Class<?>> unsupportedTypes = new HashSet<>();

    public CacheMessageDescriptorFactory() {
        addBuilder(new OrderedBaseMessageDescriptorBuilder());
        addBuilder(new ExtraBaseMessageDescriptorBuilder());
        addBuilder(new BitBaseMessageDescriptorBuilder());
    }

    /**
     * add message descriptor builder
     *
     * @param builder message descriptor builder
     */
    public void addBuilder(BaseMessageDescriptorBuilder builder) {
        builders.add(builder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> MessageDescriptor<T> getMessageDescriptor(Class<T> messageType) {
        MessageDescriptor<?> descriptor = cache.get(messageType);
        boolean unsupported = false;
        if (descriptor == null) {
            synchronized (this) {
                if (unsupportedTypes.contains(messageType)) {
                    unsupported = true;
                } else {
                    descriptor = cache.get(messageType);
                    if (descriptor == null) {
                        for (BaseMessageDescriptorBuilder builder : builders) {
                            if (builder.supported(messageType)) {
                                descriptor = builder.build(messageType);
                                if (descriptor != null) {
                                    cache.put(messageType, descriptor);
                                } else {
                                    unsupported = true;
                                    unsupportedTypes.add(messageType);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (unsupported) {
            throw new DescriptorBuilderException(messageType.getName(), "unsupported build this message type: " + messageType);
        }
        return (MessageDescriptor<T>) descriptor;
    }
}
