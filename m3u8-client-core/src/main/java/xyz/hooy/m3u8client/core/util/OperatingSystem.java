package xyz.hooy.m3u8client.core.util;

import lombok.Getter;

@Getter
public enum OperatingSystem {
    Windows(".exe"), MacOS(".app"), Linux(".bin"), Unknown("");

    private final String suffix;

    OperatingSystem(String suffix) {
        this.suffix = suffix;
    }

    public String nameToLowerCase() {
        return name().toLowerCase();
    }
}