package net.vivatcreative.core.exceptions;

public class InvalidTitleException extends Exception {
    public InvalidTitleException(String titleKey) {
            super(titleKey);
        }
}
