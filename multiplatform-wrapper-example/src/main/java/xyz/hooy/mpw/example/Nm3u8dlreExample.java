package xyz.hooy.mpw.example;

import xyz.hooy.mpw.core.Executor;
import xyz.hooy.mpw.core.GeneralLocator;

import java.io.IOException;

public class Nm3u8dlreExample {

    public static void main(String[] args) throws IOException {
        GeneralLocator ffmpegLocator = new GeneralLocator("FFmpeg", "7.0");
        GeneralLocator nm3u8dlreLocator = new GeneralLocator("N_m3u8DL-RE", "v0.2.0-beta");
        Executor executor = nm3u8dlreLocator.createExecutor();
        executor.addArguments("https://multiplatform-wrapper.hooy.xyz/example.m3u8", "--ffmpeg-binary-path", ffmpegLocator.getExecutablePath());
        executor.execute();
        executor.destroy();
    }
}
