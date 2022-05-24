package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;
import org.mrfyo.extractor.util.WriterUtil;


/**
 * @author Feng Yong
 */
public class EnumOriginTypeHandler<E extends Enum<E>> implements TypeHandler<E> {

    private final Class<E> type;
    private final E[] enums;

    public EnumOriginTypeHandler(Class<E> type) {
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getTypeName() + " is not enum type");
        }
    }


    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, E value) throws ExtractException {
        int v = -1;
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].equals(value)) {
                v = i;
                break;
            }
        }
        if (v < 0) {
            throw new ExtractException("cannot convert " + value + " to " + descriptor.getDataType() + " by origin value");
        }
        WriterUtil.writeInt(writer, v, descriptor.getDataType());
    }

    @Override
    public E unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        int i = reader.readUint8();
        if (i < enums.length) {
            return enums[i];
        }
        throw new ExtractException("cannot convert " + i + " to " + type.getSimpleName() + " by origin value");
    }
}
