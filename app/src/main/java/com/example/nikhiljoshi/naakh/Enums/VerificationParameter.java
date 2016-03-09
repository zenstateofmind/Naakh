package com.example.nikhiljoshi.naakh.Enums;

/**
 * Created by nikhiljoshi on 3/4/16.
 */
public enum VerificationParameter {

    CONTEXT("Context"), GRAMMAR("Grammar"), SPELLING("Spelling"), TONE("Tone");

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
