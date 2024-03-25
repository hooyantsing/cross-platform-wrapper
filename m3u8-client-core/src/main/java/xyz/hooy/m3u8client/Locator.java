package xyz.hooy.m3u8client;

public interface Locator {

    String getExecutablePath();

    Wrapper createExecutor();
}
