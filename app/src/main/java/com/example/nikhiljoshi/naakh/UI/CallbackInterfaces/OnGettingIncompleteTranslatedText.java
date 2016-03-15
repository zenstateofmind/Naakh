package com.example.nikhiljoshi.naakh.UI.CallbackInterfaces;

import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;

/**
 * Created by nikhiljoshi on 3/10/16.
 */
public interface OnGettingIncompleteTranslatedText {

    void takeActionWithIncompleteTranslatedTextObject(TranslationInfo translationInfo,
                                                      TranslationStatus translationStatus);
}
