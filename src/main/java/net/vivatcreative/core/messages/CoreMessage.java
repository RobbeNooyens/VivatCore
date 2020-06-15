package net.vivatcreative.core.messages;

public enum CoreMessage implements VivatMessage {

    // No parameters
    ERROR, SOMETHING_WENT_WRONG, CONFIG_RELOADED, DATABASE_RECONNECTED, NO_PERM, TARGET_NOT_FOUND, SHOULD_BE_PLAYER,
    INVALID_ARGUMENT, VIVAT_HELP,

    // Parameters
    /**
     * Params: %message%
     */
    CUSTOM_ERROR,
    /**
     * Params: %plugin%
     */
    PLUGIN_RELOADED,
    /**
     * Params: %plugin%
     */
    COULDNT_RELOAD,
    /**
     * Params: %player%
     */
    WELCOME,
    /**
     * Params: %usage%
     */
    COMMAND_USAGE;

}
