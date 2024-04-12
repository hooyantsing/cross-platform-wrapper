package xyz.hooy.cpw.core;

import java.io.IOException;

public interface Locator {

    String getExecutablePath() throws IOException;

    Executor createExecutor();
}
