package org.mrfyo.extractor.type;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mrfyo.extractor.io.ByteBufHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Feng Yong
 */
public class LongTypeHandlerTest extends FixedLengthDataTypeHandlerTest<Long> {

    private static final LongTypeHandler typeHandler = new LongTypeHandler();


    @Override
    @ParameterizedTest
    @ValueSource(longs = {0, 1, 255})
    public void byteType(Long v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(byteFd.getSize());
        typeHandler.marshal(buffer, byteFd, v);
        assertEquals(byteFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, byteFd));
    }

    @Override
    @ParameterizedTest
    @ValueSource(longs = {0, 1, 0xffff})
    public void wordType(Long v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(wordFd.getSize());
        typeHandler.marshal(buffer, wordFd, v);
        assertEquals(wordFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, wordFd));
    }

    @Override
    @ParameterizedTest
    @ValueSource(longs = {0, 1, 0x1fffffff, 0xffffffffL})
    public void dwordType(Long v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(dwordFd.getSize());
        typeHandler.marshal(buffer, dwordFd, v);
        assertEquals(dwordFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, dwordFd));
    }
}
