package xyz.hooy.m3u8client.nm3u8dlre;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.hooy.m3u8client.Wrapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class NM3u8DlReWrapper implements Wrapper {

    private final String m3u8ClientExecutablePath;

    private final ArrayList<String> args = new ArrayList<>();

    private Process m3u8Client = null;

    @Getter
    private InputStream inputStream = null;

    @Getter
    private OutputStream outputStream = null;

    @Getter
    private InputStream errorStream = null;

    public NM3u8DlReWrapper(String m3u8ClientExecutablePath) {
        this.m3u8ClientExecutablePath = m3u8ClientExecutablePath;
    }

    @Override
    public void addArgument(String arg) {
        args.add(arg);
    }

    @Override
    public void execute() throws IOException {
        Stream<String> execArgs = Stream.concat(Stream.of(m3u8ClientExecutablePath), args.stream());
        execArgs = enhanceArguments(execArgs);
        List<String> execList = execArgs.collect(Collectors.toList());
        log.debug("About to execute {}", String.join(" ", execList));

        Runtime runtime = Runtime.getRuntime();
        m3u8Client = runtime.exec(execList.toArray(new String[0]));
        inputStream = m3u8Client.getInputStream();
        outputStream = m3u8Client.getOutputStream();
        errorStream = m3u8Client.getErrorStream();
    }

    @Override
    public void destroy() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Throwable t) {
                log.warn("Error closing input stream", t);
            }
            inputStream = null;
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (Throwable t) {
                log.warn("Error closing output stream", t);
            }
            outputStream = null;
        }
        if (errorStream != null) {
            try {
                errorStream.close();
            } catch (Throwable t) {
                log.warn("Error closing error stream", t);
            }
            errorStream = null;
        }
        if (m3u8Client != null) {
            m3u8Client.destroy();
            m3u8Client = null;
        }
    }
}
