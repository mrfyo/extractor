package org.mrfyo.protocol.extractor;

/**
 * Protocol Extracting Root Exception
 *
 * @author Feng Yong
 */
public class ExtractException extends RuntimeException {

    public ExtractException() {
    }

    public ExtractException(String message) {
        super(message);
    }

    public ExtractException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtractException(Throwable cause) {
        super(cause);
    }
}
