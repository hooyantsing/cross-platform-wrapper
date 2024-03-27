package xyz.hooy.example;

import xyz.hooy.m3u8client.core.Executor;
import xyz.hooy.m3u8client.core.GeneralLocator;

import java.io.IOException;

public class Nm3u8dlreExample {

    public static void main(String[] args) throws IOException {
        GeneralLocator locator = new GeneralLocator("N_m3u8DL-RE", "v0.2.0-beta");
        Executor executor = locator.createExecutor();
        executor.addArguments("https://m3u8-client.hooy.xyz/example.m3u8");
        executor.execute();
        executor.destroy();
    }
}
