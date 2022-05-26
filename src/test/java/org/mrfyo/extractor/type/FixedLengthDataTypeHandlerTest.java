package org.mrfyo.extractor.type;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.io.ByteBufHelper;

import java.util.Random;

import static org.mockito.Mockito.when;

/**
 * 字节数固定的字段处理器测试
 *
 * @author Feng Yong
 */
public abstract class FixedLengthDataTypeHandlerTest<T> {

    private final static Random RANDOM = new Random();

    @Mock
    protected FieldDescriptor byteFd;

    @Mock
    protected FieldDescriptor wordFd;

    @Mock
    protected FieldDescriptor dwordFd;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        int size = DataType.BYTE.getSize();
        when(byteFd.getDataType()).thenReturn(DataType.BYTE);
        when(byteFd.getSize()).thenReturn(size);

        size = DataType.WORD.getSize();
        when(wordFd.getDataType()).thenReturn(DataType.WORD);
        when(wordFd.getSize()).thenReturn(size);
        ByteBufHelper buffer = ByteBufHelper.buffer(size);

        size = DataType.DWORD.getSize();
        when(dwordFd.getDataType()).thenReturn(DataType.DWORD);
        when(dwordFd.getSize()).thenReturn(size);
    }


    public abstract void byteType(T v);

    public abstract void wordType(T v);

    public abstract void dwordType(T v);
}
