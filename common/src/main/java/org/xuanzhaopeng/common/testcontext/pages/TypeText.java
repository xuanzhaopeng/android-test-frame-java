package org.xuanzhaopeng.common.testcontext.pages;

public enum TypeText {
    TYPE_TEXT(false), CLEAN_TEXT_THEN_TYPE(true);

    private boolean cleanText;
    TypeText(boolean cleanText) {
        this.cleanText = cleanText;
    }
}
