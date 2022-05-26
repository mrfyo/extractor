package org.mrfyo.extractor.type;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mrfyo.extractor.io.ByteBufHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Feng Yong
 */
public class ShortTypeHandlerTest extends FixedLengthDataTypeHandlerTest<Short> {

    private static final ShortTypeHandler typeHandler = new ShortTypeHandler();

    @Override
    @ParameterizedTest
    @ValueSource(shorts = {0, 1, 255})
    public void byteType(Short v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(byteFd.getSize());
        typeHandler.marshal(buffer, byteFd, v);
        assertEquals(byteFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, byteFd));
    }

    @Override
    @ParameterizedTest
    @ValueSource(shorts = {0, 1, (short) 0xffff})
    public void wordType(Short v) {
        ByteBufHelper buffer = ByteBufHelper.buffer(wordFd.getSize());
        typeHandler.marshal(buffer, wordFd, v);
        assertEquals(wordFd.getSize(), buffer.writeIndex());
        assertEquals(v, typeHandler.unmarshal(buffer, wordFd));
    }

    @Override
    @ParameterizedTest
    @ValueSource(shorts = {0, 1})
    public void dwordType(Short v) {
        assertThrows(TypeHandleException.class, () -> {
            ByteBufHelper buffer = ByteBufHelper.buffer(dwordFd.getSize());
            typeHandler.marshal(buffer, dwordFd, v);
            assertEquals(dwordFd.getSize(), buffer.writeIndex());
            assertEquals(v, typeHandler.unmarshal(buffer, dwordFd));
        });
    }
}
