package org.mrfyo.protocol.extractor.annotation;


import org.mrfyo.protocol.extractor.enums.MessageType;

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
    MessageType type() default MessageType.FIX;

    /**
     * 是否为下行消息
     *
     * @return 如果主机发往中断，返回 true，否则返回 false
     */
    boolean reply() default false;
}
