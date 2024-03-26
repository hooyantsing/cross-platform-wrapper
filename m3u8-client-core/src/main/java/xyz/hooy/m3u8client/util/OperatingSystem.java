package xyz.hooy.m3u8client.util;

import lombok.Getter;

@Getter
public enum OperatingSystem {
    Windows("win", ".exe"), MacOS("macos", "-osx"), Linux("linux", ""), Unknown("", "");

    private final String abbreviation;
    private final String suffix;

    OperatingSystem(String abbreviation, String suffix) {
        this.abbreviation = abbreviation;
        this.suffix = suffix;
    }
}