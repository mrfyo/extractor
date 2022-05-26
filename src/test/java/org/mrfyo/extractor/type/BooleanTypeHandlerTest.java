package org.mrfyo.extractor.type;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mrfyo.extractor.io.ByteBufHelper;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BooleanTypeHandlerTest extends FixedLengthDataTypeHandlerTest<Boolean>{

    private static final BooleanTypeHandler typeHandler = new BooleanTypeHandler();

    @Override
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void byteType(Boolean v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(byteFd.getSize());
        typeHandler.marshal(buffer, byteFd, v);
        assertEquals(byteFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, byteFd));
    }

    @Override
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void wordType(Boolean v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(wordFd.getSize());
        typeHandler.marshal(buffer, wordFd, v);
        assertEquals(wordFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, wordFd));
    }

    @Override
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void dwordType(Boolean v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(dwordFd.getSize());
        typeHandler.marshal(buffer, dwordFd, v);
        assertEquals(dwordFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, dwordFd));
    }
}