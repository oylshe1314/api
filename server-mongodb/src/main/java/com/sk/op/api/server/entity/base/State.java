package com.sk.op.api.server.entity.base;

public enum State {

    disable(0, "禁用"),

    enable(1, "启用"),
    ;

    private final int value;
    private final String desc;

    State(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int value() {
        return value;
    }

    public String desc() {
        return this.desc;
    }

    public static State valueOf(Integer value) {
        return value == null || value == disable.value() ? disable : enable;
    }

    public static String desc(int value) {
        State state = valueOf(value);
        return state == null ? "未知" : state.desc();
    }

    public static boolean enable(int value) {
        return value != disable.value();
    }

    public static boolean enable(Integer value) {
        return value != null && enable(value.intValue());
    }

}
