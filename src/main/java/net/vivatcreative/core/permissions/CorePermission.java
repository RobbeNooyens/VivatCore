package net.vivatcreative.core.permissions;

public enum CorePermission implements VivatPermission {
    EQUIP_TITLE("rank.title.%s"),
    VIVAT_STAFF("vivat.staff"),
    MESSAGE("vivat.command.message.%s");

    private final String node;

    CorePermission(String node) {
        this.node = node;
    }

    @Override
    public String getNode() {
        return node;
    }
}
