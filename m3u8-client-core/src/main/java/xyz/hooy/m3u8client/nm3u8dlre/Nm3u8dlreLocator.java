package xyz.hooy.m3u8client.nm3u8dlre;

import lombok.extern.slf4j.Slf4j;
import xyz.hooy.m3u8client.AbstractLocator;
import xyz.hooy.m3u8client.Executor;
import xyz.hooy.m3u8client.GeneralExecutor;

import java.io.IOException;

@Slf4j
public class Nm3u8dlreLocator extends AbstractLocator {

    public Nm3u8dlreLocator(String clientName, String clientVersion) throws IOException {
        super(clientName, clientVersion);
    }

    @Override
    public Executor createExecutor() {
        return new GeneralExecutor(getExecutablePath());
    }
}
