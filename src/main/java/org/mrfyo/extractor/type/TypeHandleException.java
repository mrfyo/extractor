package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;

/**
 * @author Feng Yong
 */
public class TypeHandleException extends ExtractException {

    public TypeHandleException() {
    }

    public TypeHandleException(String message) {
        super(message);
    }

    public TypeHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}
