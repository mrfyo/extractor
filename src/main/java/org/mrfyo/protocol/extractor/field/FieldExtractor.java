package org.mrfyo.protocol.extractor.field;


/**
 * 字段解析器
 * @author Feyon
 * @date 2021/8/4
 */
public interface FieldExtractor<T> extends FieldSerializer, FieldDeserializer<T> {

//    /**
//     * create default field value extractor
//     * @return {@link FieldExtractorAggregator}
//     */
//    static FieldExtractor<Object> create() {
//        return new FieldExtractorAggregator();
//    }

}
