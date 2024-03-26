package xyz.hooy.m3u8client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Executor {

    void addArguments(String... args);

    void execute() throws IOException;

    void destroy();

    OutputStream getOutputStream();

    InputStream getInputStream();

    InputStream getErrorStream();
}
