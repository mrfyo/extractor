package org.mrfyo.extractor.extractor;

import org.mrfyo.extractor.bean.MessageDescriptor;
import org.mrfyo.extractor.factory.MessageDescriptorFactory;
import org.mrfyo.extractor.factory.CacheMessageDescriptorFactory;

import org.mrfyo.extractor.type.TypeHandlerAggregator;
import org.mrfyo.extractor.type.TypeHandlerRegistry;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 消息解析聚合器
 *
 * @author Feyon
 * @date 2021/8/11
 */
public class MessageExtractorAggregator implements MessageExtractor {

    private final List<MessageExtractor> extractors = new ArrayList<>(5);

    private final MessageDescriptorFactory descriptorFactory;

    private final TypeHandlerRegistry registry;

    private final TypeHandlerAggregator typeHandlerAggregator;

    public MessageExtractorAggregator() {
        this(new TypeHandlerRegistry());
    }

    public MessageExtractorAggregator(TypeHandlerRegistry registry) {
        this.descriptorFactory = new CacheMessageDescriptorFactory();
        this.registry = registry;
        this.typeHandlerAggregator = new TypeHandlerAggregator(registry);
        addExtractor(new FixedMessageExtractor(typeHandlerAggregator, descriptorFactory));
        addExtractor(new ExtraMessageExtractor(typeHandlerAggregator, descriptorFactory));
    }


    @Override
    public boolean supported(MessageDescriptor<?> descriptor) {
        for (MessageExtractor extractor : extractors) {
            if (extractor.supported(descriptor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object unmarshal(Reader reader, MessageDescriptor<?> descriptor) {
        for (MessageExtractor extractor : extractors) {
            if (extractor.supported(descriptor)) {
                return extractor.unmarshal(reader, descriptor);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unmarshal(Reader reader, Class<T> clazz) {
        MessageDescriptor<?> descriptor = descriptorFactory.getMessageDescriptor(clazz);
        return (T) unmarshal(reader, descriptor);
    }

    @Override
    public void marshal(Writer writer, MessageDescriptor<?> descriptor, Object bean) {

    }

    @Override
    public <T> void marshal(Writer writer, T bean) {
        MessageDescriptor<?> descriptor = descriptorFactory.getMessageDescriptor(bean.getClass());
        for (MessageExtractor extractor : extractors) {
            if (extractor.supported(descriptor)) {
                extractor.marshal(writer, descriptor, bean);
                return;
            }
        }
    }

    public void addExtractor(MessageExtractor extractor) {
        this.extractors.add(extractor);
    }

    public void addAllExtractor(Collection<MessageExtractor> extractors) {
        this.extractors.addAll(extractors);
    }

    public TypeHandlerRegistry getRegistry() {
        return registry;
    }

    public TypeHandlerAggregator getFieldExtractorAggregator() {
        return typeHandlerAggregator;
    }

    public MessageDescriptorFactory getDescriptorFactory() {
        return descriptorFactory;
    }
}
