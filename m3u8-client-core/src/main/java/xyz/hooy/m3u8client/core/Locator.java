package xyz.hooy.m3u8client.core;

import java.io.IOException;

public interface Locator {

    String getExecutablePath() throws IOException;

    Executor createExecutor();
}
