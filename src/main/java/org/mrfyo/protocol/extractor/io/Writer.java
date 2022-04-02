package org.mrfyo.protocol.extractor.io;

/**
 * @author Feyon
 * @date 2021/8/8
 */
public interface Writer {
    /**
     * write uint8
     *
     * @param v uint8 value
     * @return this writer
     */
    Writer writeUint8(int v);

    /**
     * write uint16
     *
     * @param v uint16 value
     * @return this writer
     */
    Writer writeUint16(int v);

    /**
     * write uint32
     *
     * @param v uint32 value
     * @return this writer
     */
    Writer writeUint32(int v);

    /**
     * write byte array
     *
     * @param bytes byte array
     * @return this writer
     */
    Writer writeBytes(byte[] bytes);

    /**
     * return current write index.
     *
     * @return current write index
     */
    int writeIndex();

    /**
     * set uint8 start index provided
     *
     * @param index start index
     * @param v     uint8 value
     */
    void setUint8(int index, int v);

    /**
     * set uint16 start index provided
     *
     * @param index start index
     * @param v     uint16 value
     */
    void setUint16(int index, int v);

    /**
     * set uint32 start index provided
     *
     * @param index start index
     * @param v     uint32 value
     */
    void setUint32(int index, int v);

    /**
     * return all bytes.
     *
     * @return all bytes.
     */
    byte[] byteArray();
}
