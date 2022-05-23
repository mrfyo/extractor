package org.mrfyo.extractor.factory;

import org.mrfyo.extractor.ExtractException;

/**
 * @author Feng Yong
 */
public class DescriptorBuilderException extends ExtractException {

    private final String name;

    private final String reason;

    public DescriptorBuilderException(String name, String reason) {
        super("build failing for " + name + " , because of " + reason);
        this.name = name;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }
}
