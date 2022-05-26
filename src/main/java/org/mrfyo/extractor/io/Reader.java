package org.mrfyo.extractor.io;

/**
 * @author Feyon
 * @date 2021/8/2
 */
public interface Reader {

    /**
     * read uint8 data
     *
     * @return uint8
     */
    int readUint8();

    /**
     * read uint16 data
     *
     * @return uint16
     */
    int readUint16();

    /**
     * read uint32 data
     *
     * @return uint32
     */
    long readUint32();


    /**
     * read bytes that's size equal param array length
     *
     * @param dst accepted array
     */
    void readBytes(byte[] dst);

    /**
     * read part of bytes to new {@link Reader}
     *
     * @param n need to read byte size
     * @return {@link Reader}
     */
    Reader readBytes(int n);

    /**
     * release reader
     */
    void release();

    /**
     * check is or not can read n bytes.
     *
     * @param n 试图读取的数目
     * @return 如果足够读取n个字节，返回 true
     */
    boolean isReadable(int n);

    /**
     * return is or not readable.
     *
     * @return readable bytes size.
     */
    int readableBytes();

    /**
     * reset read
     */
    void resetRead();

    /**
     * return uint8 start index provided
     *
     * @param index start index
     * @return uint8
     */
    int getUint8(int index);

    /**
     * return uint16 start index provided
     *
     * @param index start index
     * @return uint16
     */
    int getUint16(int index);

    /**
     * return uint32 start index provided
     *
     * @param index start index
     * @return uint32
     */
    long getUint32(int index);

    /**
     * return bytes start index provided
     *
     * @param index start index
     * @param dst   byte array
     */
    void getBytes(int index, byte[] dst);

    /**
     * return read index
     *
     * @return read index
     */
    int readIndex();
}
