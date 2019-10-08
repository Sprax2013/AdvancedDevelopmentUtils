package de.sprax2013.advanced_dev_utils.mineskin;

public enum Visibility {
    PUBLIC(0), PRIVATE(1);

    private final int code;

    Visibility(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}