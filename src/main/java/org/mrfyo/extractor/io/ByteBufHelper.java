package org.mrfyo.extractor.io;

import cn.hutool.core.util.HexUtil;
import org.mrfyo.extractor.ExtractException;


/**
 * @author Feyon
 * @date 2021/8/13
 */
public class ByteBufHelper implements Writer, Reader {

    private byte[] buf;

    private int readIndex;

    private int writeIndex;

    private final int initReadIndex;

    public static ByteBufHelper buffer(int cap) {
        return new ByteBufHelper(new byte[cap], 0, 0);
    }

    public static ByteBufHelper copiedBuffer(byte[] buf) {
        return new ByteBufHelper(buf, 0, buf.length);
    }

    private ByteBufHelper(byte[] b, int readIndex, int writeIndex) {
        this.buf = b;
        this.readIndex = readIndex;
        this.writeIndex = writeIndex;
        this.initReadIndex = readIndex;
    }


    @Override
    public int readUint8() {
        int off = readCheck(1);
        return buf[off] & 0xff;
    }

    @Override
    public int readUint16() {
        int off = readCheck(2);
        int a1 = buf[off] & 0xff;
        int a2 = buf[off + 1] & 0xff;
        return (a1 << 8) + a2;
    }

    @Override
    public long readUint32() {
        int off = readCheck(4);
        int a1 = buf[off] & 0xff;
        int a2 = buf[off + 1] & 0xff;
        int a3 = buf[off + 2] & 0xff;
        int a4 = buf[off + 3] & 0xff;
        return ((long) a1 << 24) + (a2 << 16) + (a3 << 8) + a4;
    }

    @Override
    public String readBcd(int n) {
        byte[] b = new byte[n];
        readBytes(b);
        return HexUtil.encodeHexStr(b);
    }

    @Override
    public void readBytes(byte[] dst) {
        int off = readCheck(dst.length);
        System.arraycopy(buf, off, dst, 0, dst.length);
    }

    @Override
    public Reader readBytes(int n) {
        int off = readCheck(n);
        return new ByteBufHelper(buf, off, off + n);
    }

    private int readCheck(int n) {
        if (!isReadable(n)) {
            throw new ExtractException("read error. out of bound.");
        }
        int off = readIndex;
        readIndex += n;
        return off;
    }

    @Override
    public void release() {

    }

    @Override
    public boolean isReadable(int n) {
        return (readIndex + n) <= writeIndex;
    }

    @Override
    public int readableBytes() {
        return writeIndex - readIndex;
    }

    @Override
    public void resetRead() {
        this.readIndex = initReadIndex;
    }

    @Override
    public int getUint8(int index) {
        int ri = readIndex(index);
        int v = readUint8();
        readIndex(ri);
        return v;
    }

    @Override
    public int getUint16(int index) {
        int ri = readIndex(index);
        int v = readUint16();
        readIndex(ri);
        return v;
    }

    @Override
    public long getUint32(int index) {
        int ri = readIndex(index);
        long v = readUint32();
        readIndex(ri);
        return v;
    }

    @Override
    public void getBytes(int index, byte[] dst) {
        int ri = readIndex(index);
        readBytes(dst);
        readIndex(ri);
    }

    @Override
    public int readIndex() {
        return readIndex;
    }

    private int readIndex(int index) {
        int ri = this.readIndex;
        this.readIndex = index;
        return ri;
    }

    @Override
    public Writer writeUint8(int v) {
        int off = writeCheck(1);
        buf[off] = (byte) v;
        return this;
    }

    @Override
    public Writer writeUint16(int v) {
        int off = writeCheck(2);
        buf[off] = (byte) (v >> 8);
        buf[off + 1] = (byte) (v);
        return this;
    }

    @Override
    public Writer writeUint32(int v) {
        int off = writeCheck(4);
        buf[off] = (byte) (v >> 24);
        buf[off + 1] = (byte) (v >> 16);
        buf[off + 2] = (byte) (v >> 8);
        buf[off + 3] = (byte) (v);
        return this;
    }

    @Override
    public Writer writeBytes(byte[] bytes) {
        int off = writeCheck(bytes.length);
        System.arraycopy(bytes, 0, buf, off, bytes.length);
        return this;
    }

    @Override
    public int writeIndex() {
        return this.writeIndex;
    }

    private int writeIndex(int index) {
        int wi = this.writeIndex;
        this.writeIndex = index;
        return wi;
    }

    @Override
    public void setUint8(int index, int v) {
        int wi = writeIndex(index);
        writeUint8(v);
        writeIndex(wi);
    }

    @Override
    public void setUint16(int index, int v) {
        int wi = writeIndex(index);
        writeUint16(v);
        if (writeIndex() < wi) {
            writeIndex(wi);
        }
    }

    @Override
    public void setUint32(int index, int v) {
        int wi = writeIndex(index);
        writeUint16(writeIndex);
        if (writeIndex() < wi) {
            writeIndex(wi);
        }
    }

    private int writeCheck(int n) {
        if (!writeable(n)) {
            throw new ExtractException("write error. writeIndex >= cap");
        }
        if (writeIndex + n >= buf.length) {
            byte[] oldBuf = this.buf;
            this.buf = new byte[buf.length * 2];
            System.arraycopy(oldBuf, 0, buf, 0, oldBuf.length);
        }
        int off = writeIndex;
        writeIndex += n;
        return off;
    }

    public boolean writeable(int n) {
        return (writeIndex + n) < Integer.MAX_VALUE;
    }

    @Override
    public byte[] byteArray() {
        int len = writeIndex - readIndex;
        byte[] b = new byte[len];
        System.arraycopy(buf, readIndex, b, 0, len);
        return b;
    }

    @Override
    public String toString() {
        return "ByteBufHelper{" +
                "readIndex=" + readIndex +
                ", writeIndex=" + writeIndex +
                ", cap=" + buf.length +
                '}';
    }
}
