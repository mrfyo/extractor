package org.mrfyo.protocol.extractor.bean;


import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.annotation.FixedField;
import org.mrfyo.protocol.extractor.enums.RawDataType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.StringJoiner;

/**
 * @author Feyon
 * @date 2021/5/30
 */
public class BasicFieldDescriptor extends AbstractFieldDescriptor {

    private final int order;

    private final int size;

    private final RawDataType rawType;

    public BasicFieldDescriptor(Field field, PropertyDescriptor pd){
        super(field, pd);
        FixedField fixedField = getAnnotation(FixedField.class, "BasicField Required");
        this.order = fixedField.order();
        this.rawType = fixedField.type();
        this.size = initSize(fixedField);
    }

    public int initSize(FixedField fixedField) {
        int length = fixedField.type().getSize();
        if(length == 0) {
            return fixedField.size();
        }
        return length;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public RawDataType getRawType() {
        return rawType;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "(", ")");
        joiner.add("order=" + getOrder());
        joiner.add("name=" + getName());
        joiner.add("type=" + getRawType());
        joiner.add("javaType=" + getJavaType());
        joiner.add("size=" + getSize());
        return "BasicFieldDescriptor" + joiner;
    }

}
