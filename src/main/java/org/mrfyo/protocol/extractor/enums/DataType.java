package org.mrfyo.protocol.extractor.enums;

/**
 * @author Feyon
 * @date 2021/8/2
 */
public enum DataType {
    /**
     * Raw data type
     */
    BYTE(1), WORD(2), DWORD(4), BYTES(0);

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
