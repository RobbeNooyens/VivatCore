package net.vivatcreative.core.messages;

public interface VivatMessage {
    default String key(){
        return this.toString().toLowerCase();
    }
}
