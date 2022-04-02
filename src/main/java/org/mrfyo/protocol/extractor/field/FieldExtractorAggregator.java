package org.mrfyo.protocol.extractor.field;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.HexUtil;
import org.mrfyo.protocol.extractor.ExtractException;
import org.mrfyo.protocol.extractor.annotation.ScaleConverter;
import org.mrfyo.protocol.extractor.annotation.Support;
import org.mrfyo.protocol.extractor.bean.FieldDescriptor;
import org.mrfyo.protocol.extractor.enums.JavaDataType;
import org.mrfyo.protocol.extractor.enums.RawDataType;
import org.mrfyo.protocol.extractor.io.Reader;
import org.mrfyo.protocol.extractor.io.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 字段解析器简单实现类
 *
 * @author Feyon
 * @date 2021/8/4
 */
public class FieldExtractorAggregator implements FieldExtractor<Object> {

    private final Logger log = LoggerFactory.getLogger(FieldExtractorAggregator.class);



    @Override
    public Object unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        int size = descriptor.getSize();
        RawDataType rawType = descriptor.getRawType();

        try {
            if (descriptor.getJavaType().equals(JavaDataType.ANY)) {
                return unmarshalBytes(reader, descriptor);
            }
            return switch (rawType) {
                case BYTE -> unmarshalUint8(reader.readUint8(), descriptor);
                case WORD -> unmarshalUint16(reader.readUint16(), descriptor);
                case DWORD -> unmarshalUint32(reader.readUint32(), descriptor);
                case BYTES -> unmarshalBytes(reader.readBytes(size), descriptor);
            };
        } catch (Exception e) {
            throw new ExtractException(e);
        } finally {
            reader.release();
        }

    }

    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Object value) throws ExtractException {
        if (value == null) {
            return;
        }
        if (writer == null || descriptor == null) {
            log.warn("writer | descriptor cannot be null");
            return;
        }
        doMarshal(writer, descriptor, value);
    }

    private void doMarshal(Writer writer, FieldDescriptor descriptor, Object value) {
        try {
            RawDataType rawType = descriptor.getRawType();
            if (descriptor.getJavaType().equals(JavaDataType.ANY)) {
                marshalBytes(writer, value, descriptor);
                return;
            }
            switch (rawType) {
                case BYTE -> writer.writeUint8(marshalUint8(value, descriptor));
                case WORD -> writer.writeUint16(marshalUint16(value, descriptor));
                case DWORD -> writer.writeUint32(marshalUint32(value, descriptor));
                case BYTES -> marshalBytes(writer, value, descriptor);
                default -> {
                    log.warn("unknown rawType for {}", rawType);
                }
            }
        } catch (Exception e) {
            throw new ExtractException(e);
        }

    }

    protected Object unmarshalUint8(int v, FieldDescriptor descriptor) {
        if (descriptor.getJavaType().equals(JavaDataType.DOUBLE)) {
            return unmarshalDouble(v, descriptor);
        }
        return v;
    }

    protected Object unmarshalUint16(int v, FieldDescriptor descriptor) {
        if (descriptor.getJavaType().equals(JavaDataType.DOUBLE)) {
            return unmarshalDouble(v, descriptor);
        }
        return v;
    }

    protected Object unmarshalUint32(long v, FieldDescriptor descriptor) {
        if (descriptor.getJavaType().equals(JavaDataType.DOUBLE)) {
            return unmarshalDouble(v, descriptor);
        }
        return v;
    }

    protected Object unmarshalDouble(double v, FieldDescriptor descriptor) {
        ScaleConverter converter = descriptor.getAnnotation(ScaleConverter.class);
        double mul = converter.mul();
        double offset = converter.offset();
        int scale = converter.scale();
        return BigDecimal.valueOf(v * mul + offset).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }


    private Object unmarshalBytes(Reader r, FieldDescriptor descriptor) throws Exception {
        Object obj = tryUnmarshalBytes(r, descriptor);
        if (obj == null) {
            Class<?> fieldType = descriptor.getFieldType();
            if (fieldType == List.class) {
                return new ArrayList<>(1);
            }
            if (fieldType == Set.class) {
                return new HashSet<>(2);
            }
        }
        return obj;
    }

    private Object tryUnmarshalBytes(Reader r, FieldDescriptor descriptor) throws Exception {
        if (descriptor.getJavaType().equals(JavaDataType.ANY)) {
            Support support = descriptor.getAnnotation(Support.class);
            if (support != null) {
                return support.value().getDeclaredConstructor().newInstance().unmarshal(r, descriptor);
            }
            throw new ProtocolException("unknown type must provide Deserializer or Support Annotation");
        } else {
            byte[] b = new byte[r.readableBytes()];
            r.readBytes(b);
            return HexUtil.encodeHexStr(b);
        }
    }

    protected int marshalUint8(Object b, FieldDescriptor descriptor) {
        if (descriptor.getJavaType().equals(JavaDataType.DOUBLE)) {
            return marshalDouble(Convert.convert(Double.class, b), descriptor);
        } else {
            return (int) b;
        }
    }

    protected int marshalUint16(Object b, FieldDescriptor descriptor) {
        if (descriptor.getJavaType().equals(JavaDataType.DOUBLE)) {
            return marshalDouble(Convert.convert(Double.class, b), descriptor);
        } else {
            return (int) b;
        }
    }

    protected int marshalUint32(Object b, FieldDescriptor descriptor) throws Exception {
        if (descriptor.getJavaType().equals(JavaDataType.DOUBLE)) {
            return marshalDouble(Convert.convert(Double.class, b), descriptor);
        } else {
            return (int) b;
        }
    }

    protected int marshalDouble(Object b, FieldDescriptor descriptor) {
        double v = (double) b;
        ScaleConverter converter = descriptor.getAnnotation(ScaleConverter.class);
        double mul = converter.mul();
        double offset = converter.offset();

        return (int) Math.round(((v - offset) / (mul)));
    }


    protected void marshalBytes(Writer w, Object b, FieldDescriptor descriptor) throws Exception {
        if (descriptor.getJavaType().equals(JavaDataType.ANY)) {
            Support support = descriptor.getAnnotation(Support.class);
            if (support != null) {
                support.value().getDeclaredConstructor().newInstance().marshal(w, descriptor, b);
            }
        } else {
            w.writeBytes(HexUtil.decodeHex((String) b));
        }
    }
}
