package org.mrfyo.extractor.type;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mrfyo.extractor.io.ByteBufHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Feng Yong
 */
public class IntTypeHandlerTest extends FixedLengthDataTypeHandlerTest<Integer> {

    private static final IntegerTypeHandler typeHandler = new IntegerTypeHandler();


    @Override
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 255})
    public void byteType(Integer v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(byteFd.getSize());
        typeHandler.marshal(buffer, byteFd, v);
        assertEquals(byteFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, byteFd));
    }

    @Override
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 0xffff})
    public void wordType(Integer v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(wordFd.getSize());
        typeHandler.marshal(buffer, wordFd, v);
        assertEquals(wordFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, wordFd));
    }

    @Override
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 0xffffffff, 0x1fffffff})
    public void dwordType(Integer v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(dwordFd.getSize());
        typeHandler.marshal(buffer, dwordFd, v);
        assertEquals(dwordFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, dwordFd));
    }
}
