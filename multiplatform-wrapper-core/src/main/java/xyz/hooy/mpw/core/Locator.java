package xyz.hooy.mpw.core;

import java.io.IOException;

public interface Locator {

    String getExecutablePath() throws IOException;

    Executor createExecutor();
}
