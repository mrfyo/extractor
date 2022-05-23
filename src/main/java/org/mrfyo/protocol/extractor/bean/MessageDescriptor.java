package org.mrfyo.protocol.extractor.bean;


import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.enums.InternalMessageType;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Feyon
 * @date 2021/8/2
 */
public class MessageDescriptor<T> {
    /**
     * message id
     */
    private final int id;

    /**
     * message description
     */
    private final String desc;

    /**
     * java type
     */
    private final Class<T> javaType;

    /**
     * message type {@link InternalMessageType}
     */
    private final InternalMessageType messageType;

    /**
     * filed list
     */
    protected List<FieldDescriptor> fieldDescriptors = Collections.emptyList();

    /**
     * annotations array
     */
    private final Annotation[] annotations;


    public MessageDescriptor(Class<T> beanClass) {
        Message message = beanClass.getAnnotation(Message.class);
        if(message == null) {
            throw new IllegalArgumentException("must have Message annotation.");
        }
        this.id = message.id();
        this.desc = message.desc();
        this.javaType = beanClass;
        this.messageType = message.type();
        this.annotations = beanClass.getAnnotations();
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public Class<T> getJavaType() {
        return javaType;
    }

    public InternalMessageType getMessageType() {
        return messageType;
    }

    public void setFieldDescriptors(List<FieldDescriptor> fieldDescriptors) {
        this.fieldDescriptors = fieldDescriptors;
    }

    public List<FieldDescriptor> getFieldDescriptors() {
        return fieldDescriptors;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "(", ")");
        joiner.add("order=" + getId());
        joiner.add("desc=" + getDesc());
        return "ExtraFieldDescriptor" + joiner;
    }


    public static <T> MessageDescriptor<T> create(Class<T> clazz) {
        return new MessageDescriptor<>(clazz);
    }


    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        for (Annotation annotation : annotations) {
            if(annotationType.isInstance(annotation)) {
                return annotationType.cast(annotation);
            }
        }
        return null;
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
        return getAnnotation(annotationType) != null;
    }
}
