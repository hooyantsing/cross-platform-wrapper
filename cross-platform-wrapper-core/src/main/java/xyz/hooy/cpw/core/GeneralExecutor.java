package xyz.hooy.cpw.core;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class GeneralExecutor implements Executor {

    private Process executableClient = null;

    private final String executablePath;

    private final ArrayList<String> arguments = new ArrayList<>();

    private InputStream inputStream = null;

    private OutputStream outputStream = null;

    private InputStream errorStream = null;

    public GeneralExecutor(String executablePath) {
        this.executablePath = executablePath;
    }

    @Override
    public void addArguments(String... args) {
        arguments.addAll(Arrays.asList(args));
    }

    @Override
    public void execute() throws IOException {
        destroy();
        Stream<String> execArgs = Stream.concat(Stream.of(executablePath), arguments.stream());
        execArgs = enhanceArguments(execArgs);
        List<String> execList = execArgs.collect(Collectors.toList());
        log.debug("About to execute {}", String.join(" ", execList));
        executableClient = new ProcessBuilder().command(execList.toArray(new String[0])).start();
        GeneralKiller killer = new GeneralKiller(executableClient);
        Runtime.getRuntime().addShutdownHook(killer);
        inputStream = executableClient.getInputStream();
        outputStream = executableClient.getOutputStream();
        errorStream = executableClient.getErrorStream();
    }

    @Override
    public void destroy() {
        if (Objects.nonNull(inputStream)) {
            try {
                inputStream.close();
            } catch (Throwable t) {
                log.warn("Error closing input stream", t);
            }
            inputStream = null;
        }
        if (Objects.nonNull(outputStream)) {
            try {
                outputStream.close();
            } catch (Throwable t) {
                log.warn("Error closing output stream", t);
            }
            outputStream = null;
        }
        if (Objects.nonNull(errorStream)) {
            try {
                errorStream.close();
            } catch (Throwable t) {
                log.warn("Error closing error stream", t);
            }
            errorStream = null;
        }
        if (Objects.nonNull(executableClient) && executableClient.isAlive()) {
            executableClient.destroy();
            executableClient = null;
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public InputStream getErrorStream() {
        return errorStream;
    }

    public Stream<String> enhanceArguments(Stream<String> stream) {
        return stream;
    }
}
