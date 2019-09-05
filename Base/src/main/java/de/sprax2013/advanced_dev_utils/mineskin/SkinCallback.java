package de.sprax2013.advanced_dev_utils.mineskin;

@SuppressWarnings("unused")
public interface SkinCallback {
    void done(Skin skin);

    default void waiting(long delay) {
    }

    default void uploading() {
    }

    default void error(String errorMessage) {
    }

    default void exception(Exception exception) {
    }

    default void parseException(Exception exception, String body) {
    }
}