package org.mrfyo.extractor.bean;

import org.mrfyo.extractor.annotation.BitField;
import org.mrfyo.extractor.enums.DataType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * @author Feng Yong
 */
public class BitFieldDescriptor extends AbstractFieldDescriptor {

    private final int index;

    public BitFieldDescriptor(Field field, PropertyDescriptor pd) {
        super(field, pd);
        BitField bitField = getAnnotation(BitField.class, "bitField");
        this.index = bitField.index();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public DataType getDataType() {
        return DataType.BYTE;
    }
}
