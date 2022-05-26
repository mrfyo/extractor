package org.mrfyo.extractor.annotation;

import java.lang.annotation.*;

/**
 * @author Feng Yong
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OrderMessage {
    /**
     * 消息ID
     */
    int id() default 0;

    /**
     * 消息描述
     */
    String desc() default "";
}
