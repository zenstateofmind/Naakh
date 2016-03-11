package com.example.nikhiljoshi.naakh.UI.CallbackInterfaces;

import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

/**
 * Created by nikhiljoshi on 3/10/16.
 */
public interface OnGettingIncompleteTranslatedText {

    void takeActionWithIncompleteTranslatedTextObject(TranslationInfoPojo translationInfoPojo);
}
