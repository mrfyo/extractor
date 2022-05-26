package org.mrfyo.extractor.util;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class WriterUtil {

    public static void writeInt(Writer w, int value, DataType dataType) {
        if (dataType == null) {
            throw new IllegalArgumentException("dataType cannot null");
        }
        if (DataType.BYTES.equals(dataType)) {
            throw new IllegalArgumentException("unsupported dataType: " + dataType);
        }
        writeInt(w, value, dataType.getSize());
    }

    public static void writeInt(Writer w, int value, int size) {
        if (w == null) {
            throw new IllegalArgumentException("writer cannot null");
        }
        if (size == DataType.BYTE.getSize()) {
            w.writeUint8(value);
        } else if (size == DataType.WORD.getSize()) {
            w.writeUint16(value);
        } else if (size == DataType.DWORD.getSize()) {
            w.writeUint32(value);
        } else {
            throw new ExtractException("cannot write " + size + "byte");
        }
    }


}
