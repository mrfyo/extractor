package org.mrfyo.extractor.factory;


import org.mrfyo.extractor.bean.MessageDescriptor;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/**
 * @author Feng Yong
 */
public abstract class BaseMessageDescriptorBuilder {


    /**
     * return is or not support building.
     *
     * @param messageType message class type
     * @return is or not support building
     */
    abstract boolean supported(Class<?> messageType);

    /**
     * build message descriptor.
     *
     * @param messageType message class type
     * @param <T>         message type
     * @return message descriptor
     * @throws DescriptorBuilderException if build fail.
     */
    abstract <T> MessageDescriptor<T> build(Class<T> messageType) throws DescriptorBuilderException;


    protected PropertyDescriptor getPropertyDescriptor(String fieldName, Class<?> type) throws DescriptorBuilderException {
        try {
            return new PropertyDescriptor(fieldName, type);
        } catch (IntrospectionException e) {
            throw new DescriptorBuilderException(fieldName, "field cannot visit");
        }
    }

    protected List<Field> getDeclaredFields(Class<?> clazz, boolean includeSupper, Predicate<Field> matcher) {
        List<Field> fields = new ArrayList<>();
        if(includeSupper) {
            Class<?> superclass = clazz.getSuperclass();
            if(superclass != null) {
                Field[] fs = superclass.getDeclaredFields();
                for (Field f : fs) {
                    if(matcher.test(f)){
                        fields.add(f);
                    }
                }
            }
        }
        Field[] fs = clazz.getDeclaredFields();
        for (Field f : fs) {
            if(matcher.test(f)){
                fields.add(f);
            }
        }
        return fields;
    }
}
