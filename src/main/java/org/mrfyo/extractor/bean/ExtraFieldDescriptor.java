package org.mrfyo.extractor.bean;

import org.mrfyo.extractor.annotation.ExtraField;
import org.mrfyo.extractor.enums.DataType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.StringJoiner;

/**
 * @author Feyon
 * @date 2021/8/2
 */
public class ExtraFieldDescriptor extends AbstractFieldDescriptor {

    private final int id;

    private int size;

    private final DataType type;

    public ExtraFieldDescriptor(Field field, PropertyDescriptor pd) {
        super(field, pd);
        ExtraField basicField = getAnnotation(ExtraField.class, "ExtraField Required");
        this.id = basicField.id();
        this.type = basicField.rawType();
        this.size = initSize(basicField);
    }

    public int initSize(ExtraField basicField) {
        int size = basicField.rawType().getSize();
        if (size == 0) {
            return basicField.size();
        }
        return size;
    }

    public int getId() {
        return id;
    }


    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public DataType getDataType() {
        return type;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "(", ")");
        joiner.add("order=" + getId());
        joiner.add("name=" + getName());
        joiner.add("type=" + getFieldType().getName());
        joiner.add("size=" + getSize());
        return "ExtraFieldDescriptor" + joiner;
    }
}
