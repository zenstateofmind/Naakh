package com.example.nikhiljoshi.naakh.Enums;

/**
 * Different dimensions to a specific translation request. The customer can provide context,
 * Grammar and additional information. These will also be the parameters that will be used
 * while verifying a specific translation
 */
public enum VerificationParameter {

    CONTEXT("Context"), GRAMMAR("Grammar"), SPELLING("Spelling");

    private final String parameter;

    VerificationParameter(String parameter) {
        this.parameter = parameter;
    }

    public static VerificationParameter getEnum(String verification) {
        if (verification != null) {
            for (VerificationParameter parameterEnum : VerificationParameter.values()) {
                if (parameterEnum.parameter.equalsIgnoreCase(verification)) {
                    return parameterEnum;
                }
            }
        }
        return null;
    }
}
