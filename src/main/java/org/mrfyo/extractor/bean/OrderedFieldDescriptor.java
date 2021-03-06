package org.mrfyo.extractor.bean;


import org.mrfyo.extractor.annotation.OrderField;
import org.mrfyo.extractor.enums.DataType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.StringJoiner;

/**
 * @author Feyon
 * @date 2021/5/30
 */
public class OrderedFieldDescriptor extends AbstractFieldDescriptor {

    private final int order;

    private final int size;

    private final DataType dataType;

    public OrderedFieldDescriptor(Field field, PropertyDescriptor pd){
        super(field, pd);
        OrderField orderField = getAnnotation(OrderField.class, "BasicField Required");
        this.order = orderField.order();
        this.dataType = orderField.type();
        this.size = initSize(orderField);
    }

    public int initSize(OrderField orderField) {
        int length = orderField.type().getSize();
        if(length == 0) {
            return orderField.size();
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
    public DataType getDataType() {
        return dataType;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "(", ")");
        joiner.add("order=" + getOrder());
        joiner.add("name=" + getName());
        joiner.add("type=" + getDataType());
        joiner.add("size=" + getSize());
        return "OrderedFieldDescriptor" + joiner;
    }

}
