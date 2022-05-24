package org.mrfyo.extractor.type;

import cn.hutool.core.convert.Convert;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.annotation.Scale;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.mrfyo.extractor.util.ReaderUtil;
import org.mrfyo.extractor.util.WriterUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Feng Yong
 */
public class DoubleTypeHandler implements TypeHandler<Double> {
    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Double value) throws ExtractException {
        int v = inFormat(descriptor, value);
        WriterUtil.writeInt(writer, v, descriptor.getDataType());
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
        return Convert.convert(Double.class, ReaderUtil.readLong(reader, descriptor.getDataType()));
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

