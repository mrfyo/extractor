package org.mrfyo.extractor.type;

import cn.hutool.core.convert.Convert;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.annotation.Scale;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Feng Yong
 */
public class DoubleTypeHandler implements TypeHandler<Double> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Double value) throws ExtractException {
        int v = inFormat(descriptor, value);
        DataType dataType = descriptor.getDataType();
        if (dataType.equals(DataType.BYTE)) {
            writer.writeUint8(v);
        } else if (dataType.equals(DataType.WORD)) {
            writer.writeUint16(v);
        } else if (dataType.equals(DataType.DWORD)) {
            writer.writeUint32(v);
        } else {
            throw new ExtractException("cannot marshal " + dataType);
        }
    }

    private int inFormat(FieldDescriptor descriptor, double v) {
        Scale converter = descriptor.getAnnotation(Scale.class);
        if (converter != null) {
            double mul = converter.mul();
            double offset = converter.offset();

            return (int) Math.round(((v - offset) / (mul)));
        } else {
            return (int) Math.round(v);
        }
    }


    @Override
    public Double unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return outFormat(descriptor, convert(reader, descriptor));
    }

    private double convert(Reader reader, FieldDescriptor descriptor) {
        DataType dataType = descriptor.getDataType();
        if (dataType.equals(DataType.BYTE)) {
            return Convert.convert(Double.class, reader.readUint8());
        } else if (dataType.equals(DataType.WORD)) {
            return Convert.convert(Double.class, reader.readUint16());
        } else if (dataType.equals(DataType.DWORD)) {
            return Convert.convert(Double.class, reader.readUint32());
        } else {
            throw new ExtractException("cannot unmarshal " + dataType);
        }
    }

    private double outFormat(FieldDescriptor descriptor, double v) {
        Scale converter = descriptor.getAnnotation(Scale.class);
        if (converter != null) {
            double mul = converter.mul();
            double offset = converter.offset();
            int scale = converter.scale();
            return BigDecimal.valueOf(v * mul + offset).setScale(scale, RoundingMode.HALF_UP).doubleValue();
        } else {
            return v;
        }
    }
}

