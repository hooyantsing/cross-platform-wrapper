package xyz.hooy.m3u8client.core;

import lombok.extern.slf4j.Slf4j;
import xyz.hooy.m3u8client.core.util.OperatingSystem;
import xyz.hooy.m3u8client.core.util.OperatingSystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
public class GeneralLocator implements Locator {

    private final static String standardClientName = "#{CLIENT_NAME}-#{CLIENT_VERSION}-#{OPERATING_SYSTEM}-#{ARCH}#{SUFFER}";
    private final static String jarClientDirector = "xyz/hooy/m3u8client/nativebin";
    private final static String localClientDirectory = System.getProperty("java.io.tmpdir") + jarClientDirector;
    private final String clientName;
    private final String clientVersion;
    private final String executablePath;

    public GeneralLocator(String clientName, String clientVersion) throws IOException {
        this.clientName = clientName;
        this.clientVersion = clientVersion;
        createDirectory(Paths.get(localClientDirectory));
        String clientFullName = generateClientName();
        Path sourcePath = Paths.get(jarClientDirector, clientFullName);
        Path targetFile = Paths.get(localClientDirectory, clientFullName);
        initClient(sourcePath, targetFile);
        executablePath = targetFile.toString();
    }

    public String generateClientName() {
        String arch = System.getProperty("os.arch");
        OperatingSystem operatingSystem = OperatingSystemUtils.currentOperatingSystem();
        return standardClientName.replace("#{CLIENT_NAME}", clientName)
                .replace("#{CLIENT_VERSION}", clientVersion)
                .replace("#{OPERATING_SYSTEM}", operatingSystem.getAbbreviation())
                .replace("#{ARCH}", arch)
                .replace("#{SUFFER}", operatingSystem.getSuffix());
    }

    protected synchronized void initClient(Path sourcePath, Path targetPath) throws IOException {
        if (!Files.isRegularFile(targetPath)) {
            copyClient(sourcePath, targetPath);
        }
        if (OperatingSystemUtils.currentOperatingSystem() != OperatingSystem.Windows) {
            ProcessBuilder command = new ProcessBuilder().command("/bin/chmod", "755", targetPath.toString());
            command.start();
        }
    }

    protected void copyClient(Path sourcePath, Path targetPath) throws IOException {
        String source = sourcePath.toString();
        InputStream is = getClass().getResourceAsStream(source);
        if (Objects.isNull(is)) {
            // Use this for Java 9+ only if required
            is = ClassLoader.getSystemResourceAsStream(source);
            if (Objects.isNull(is)) {
                // Use this for spring boot with different class loaders
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                is = classloader.getResourceAsStream(source);
            }
        }
        if (Objects.nonNull(is)) {
            Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
            is.close();
        } else {
            throw new IOException("Not found client!");
        }
    }

    protected void createDirectory(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            Files.createDirectories(path);
        }
    }

    @Override
    public String getExecutablePath() {
        return executablePath;
    }

    @Override
    public Executor createExecutor() {
        return new GeneralExecutor(getExecutablePath());
    }
}
