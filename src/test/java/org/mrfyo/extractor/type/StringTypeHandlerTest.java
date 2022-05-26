package org.mrfyo.extractor.type;

import cn.hutool.core.util.HexUtil;
import org.junit.jupiter.api.Test;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.ByteBufHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StringTypeHandlerTest {

    private final static StringTypeHandler TYPE_HANDLER = new StringTypeHandler();


    @Test
    void marshal() {
        String s = "010203040506";
        byte[] hex = HexUtil.decodeHex(s);
        ByteBufHelper helper = ByteBufHelper.buffer(32);

        FieldDescriptor fd = mock(FieldDescriptor.class);
        when(fd.getSize()).thenReturn(hex.length);

        TYPE_HANDLER.marshal(helper, fd, s);
        assertEquals(hex.length, helper.writeIndex());
    }


    @Test
    void emptyValue() {
        ByteBufHelper helper = ByteBufHelper.buffer(32);

        FieldDescriptor fd = mock(FieldDescriptor.class);
        when(fd.getSize()).thenReturn(5);

        TYPE_HANDLER.marshal(helper, fd, "");
        assertEquals(5, helper.writeIndex());
    }

    @Test
    void notHexString() {
        assertThrows(TypeHandleException.class, () -> {
            ByteBufHelper helper = ByteBufHelper.buffer(32);

            FieldDescriptor fd = mock(FieldDescriptor.class);
            when(fd.getSize()).thenReturn(5);
            TYPE_HANDLER.marshal(helper, fd, "efg");
        });
    }

    @Test
    void unmarshal() {
        String s = "010203040506";
        byte[] hex = HexUtil.decodeHex(s);
        ByteBufHelper helper = ByteBufHelper.copiedBuffer(hex);

        FieldDescriptor fd = mock(FieldDescriptor.class);
        when(fd.getSize()).thenReturn(hex.length);
        assertEquals(s, TYPE_HANDLER.unmarshal(helper, fd));
    }
}