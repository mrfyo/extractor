package org.mrfyo.extractor.enums;

/**
 * @author Feyon
 * @date 2021/8/2
 */
public enum DataType {
    /**
     * unsigned data type
     */
    BYTE(1), WORD(2), DWORD(4),

    /**
     * signed data type
     */
    INT8(1), INT16(2), INT32(4),

    /**
     * byte array type
     */
    BYTES(0);

    /**
     * the length of bytes.
     */
    private final int size;

    DataType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
