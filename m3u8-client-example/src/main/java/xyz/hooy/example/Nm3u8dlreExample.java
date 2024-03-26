package xyz.hooy.example;

import xyz.hooy.m3u8client.core.Executor;
import xyz.hooy.m3u8client.core.nm3u8dlre.Nm3u8dlreLocator;

import java.io.IOException;

public class Nm3u8dlreExample {

    public static void main(String[] args) throws IOException {
        Nm3u8dlreLocator locator = new Nm3u8dlreLocator("N_m3u8DL-RE", "v0.2.0-beta");
        Executor executor = locator.createExecutor();
        executor.execute();
        executor.destroy();
    }
}
