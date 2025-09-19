package io.github.ferretFeet72.utils;

public class KeyBindingEntry {
    //    hold key & associated action
    public String key;
    public String action;
    public KeyBindingEntry(String key, String value) {
        this.key = key;
        this.action = value;
    }
    public KeyBindingEntry() {}
}
