package xyz.hooy.mpw.core.util;

import lombok.Getter;

@Getter
public enum OperatingSystem {
    Windows("exe"), MacOS("app"), Linux("bin"), Unknown("");

    private final String suffix;

    OperatingSystem(String suffix) {
        this.suffix = suffix;
    }

    public String nameToLowerCase() {
        return name().toLowerCase();
    }
}