package org.mrfyo.protocol.extractor.message;

import org.mrfyo.protocol.extractor.bean.MessageDescriptor;
import org.mrfyo.protocol.extractor.factory.CacheMessageDescriptorFactory;
import org.mrfyo.protocol.extractor.factory.MessageDescriptorFactory;
import org.mrfyo.protocol.extractor.field.FieldExtractor;
import org.mrfyo.protocol.extractor.field.FieldExtractorAggregator;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;

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

    private final List<MessageExtractor> extractors;

    private final MessageDescriptorFactory descriptorFactory;

    public MessageExtractorAggregator() {
        this.extractors = new ArrayList<>(5);
        this.descriptorFactory = new CacheMessageDescriptorFactory();
        FieldExtractor<Object> fieldExtractor = new FieldExtractorAggregator();
        addExtractor(new FixedMessageExtractor(fieldExtractor, descriptorFactory));
        addExtractor(new ExtraMessageExtractor(fieldExtractor, descriptorFactory));
    }


    @Override
    public boolean supported(MessageDescriptor<?> descriptor) {
        for (MessageExtractor extractor : extractors) {
            if(extractor.supported(descriptor)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object unmarshal(Reader reader, MessageDescriptor<?> descriptor) {
        for (MessageExtractor extractor : extractors) {
            if(extractor.supported(descriptor)) {
                return extractor.unmarshal(reader, descriptor);
            }
        }
        return null;
    }

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
            if(extractor.supported(descriptor)) {
                extractor.marshal(writer, descriptor, bean);
                return;
            }
        }
    }

    public void addExtractor(MessageExtractor extractor) {
        this.extractors.add(extractor);
    }

    public void addExtractorAll(Collection<MessageExtractor> extractors) {
        this.extractors.addAll(extractors);
    }

    public List<MessageExtractor> getExtractors() {
        return extractors;
    }
}
