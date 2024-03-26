package xyz.hooy.m3u8client;

import java.io.IOException;

public interface Locator {

    String getExecutablePath() throws IOException;

    Executor createExecutor();
}
