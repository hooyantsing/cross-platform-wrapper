package xyz.hooy.m3u8client;

import java.io.IOException;
import java.util.stream.Stream;

public interface Wrapper {

    default Stream<String> enhanceArguments(Stream<String> stream) {
        return stream;
    }

    void addArgument(String arg);

    void execute() throws IOException;

    void destroy();
}
