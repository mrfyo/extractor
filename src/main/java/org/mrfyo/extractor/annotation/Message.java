package org.mrfyo.extractor.annotation;


import java.lang.annotation.*;

/**
 * @author Feyon
 * @date 2021/8/2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Message {
    /**
     * 消息ID
     */
    int id() default 0;

    /**
     * 消息描述
     */
    String desc() default "";

    /**
     * 消息类型
     */
    int type() default 0;

}
