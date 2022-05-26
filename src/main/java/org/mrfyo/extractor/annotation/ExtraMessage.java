package org.mrfyo.extractor.annotation;

import org.mrfyo.extractor.enums.DataType;

import java.lang.annotation.*;

/**
 * 该注解用于描述类似于 MAP 形式的消息格式，完整的消息由若干个子消息构成，形如 [key:length:value,...]
 * @author Feng Yong
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExtraMessage {
    /**
     * 消息ID
     */
    int id() default 0;

    /**
     * 消息描述
     */
    String desc() default "";

    /**
     * 描述子消息key的数据类型，仅支持 BYTE / WORD
     */
    DataType keyDataType() default DataType.WORD;

    /**
     * 描述子消息内容长度字段的数据类型，仅支持 BYTE / WORD
     */
    DataType lengthDataType() default DataType.BYTE;
}
