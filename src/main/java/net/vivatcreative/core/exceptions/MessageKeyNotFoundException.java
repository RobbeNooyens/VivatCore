package net.vivatcreative.core.exceptions;

/**
 * Thrown when messagekey isn't found in the messages yml
 */
public class MessageKeyNotFoundException extends Exception {

    public MessageKeyNotFoundException(String key) {
        super("Key: " + key);
    }

}
