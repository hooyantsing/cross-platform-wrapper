package xyz.hooy.m3u8client.nm3u8dlre;

import lombok.extern.slf4j.Slf4j;
import xyz.hooy.m3u8client.Locator;
import xyz.hooy.m3u8client.Wrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class NM3u8DlReLocator implements Locator {

    public static final String VERSION = "v0.2.0-beta";

    private final String path;

    public NM3u8DlReLocator() {
        String os = System.getProperty("os.name").toLowerCase();
        boolean isWindows = os.contains("windows");
        boolean isMac = os.contains("mac");
        log.debug("Os name is <{}> isWindows: {} isMac: {}", os, isWindows, isMac);

        File dirFolder = new File(System.getProperty("java.io.tmpdir"), "m3u8client/nativebin");
        if (!dirFolder.exists()) {
            log.debug("Creating m3u8Client temp folder to place executables in <{}>", dirFolder.getAbsolutePath());
            dirFolder.mkdirs();
        } else {
            log.debug("m3u8Client temp folder exists in <{}>", dirFolder.getAbsolutePath());
        }

        String arch = System.getProperty("os.arch");
        String suffix = isWindows ? "win.exe" : (isMac ? "osx-osx" : "linux");
        String m3u8ClientFileName = clientName(arch, suffix);
        File m3u8ClientFile = new File(dirFolder, m3u8ClientFileName);
        log.debug("Executable path: {}", m3u8ClientFile.getAbsolutePath());

        synchronized (NM3u8DlReLocator.class) {
            if (m3u8ClientFile.exists()) {
                log.debug("Executable exists in <{}>", m3u8ClientFile.getAbsolutePath());
            } else {
                log.debug("Need to copy executable to <{}>", m3u8ClientFile.getAbsolutePath());
                copyFile(m3u8ClientFileName, m3u8ClientFile);
            }

            if (!isWindows) {
                try {
                    Runtime.getRuntime().exec(new String[]{"/bin/chmod", "755", m3u8ClientFile.getAbsolutePath()});
                } catch (IOException e) {
                    log.error("Error setting executable via chmod", e);
                }
            }
        }

        path = m3u8ClientFile.getAbsolutePath();
        if (m3u8ClientFile.exists()) {
            log.debug("m3u8Client executable found: {}", path);
        } else {
            log.error("m3u8Client executable NOT found: {}", path);
        }
    }

    @Override
    public String getExecutablePath() {
        return path;
    }

    @Override
    public Wrapper createExecutor() {
        return new NM3u8DlReWrapper(getExecutablePath());
    }

    private void copyFile(String path, File dest) {
        String resourceName = "nativebin/" + path;
        try {
            log.debug("Copy from resource <{}> to target <{}>", resourceName, dest.getAbsolutePath());
            InputStream is = getClass().getResourceAsStream(resourceName);
            if (is == null) {
                // Use this for Java 9+ only if required
                resourceName = "xyz/hooy/m3u8client/nativebin/" + path;
                log.debug("Alternative copy from SystemResourceAsStream <{}> to target <{}>", resourceName, dest.getAbsolutePath());
                is = ClassLoader.getSystemResourceAsStream(resourceName);
            }
            if (is == null) {
                // Use this for spring boot with different class loaders
                resourceName = "xyz/hooy/m3u8client/nativebin/" + path;
                log.debug("Alternative copy from Thread.currentThread().getContextClassLoader() <{}> to target <{}>", resourceName, dest.getAbsolutePath());
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                is = classloader.getResourceAsStream(resourceName);
            }
            if (is != null) {
                if (copy(is, dest.getAbsolutePath())) {
                    if (dest.exists()) {
                        log.debug("Target <{}> exists", dest.getAbsolutePath());
                    } else {
                        log.error("Target <{}> does not exist", dest.getAbsolutePath());
                    }
                } else {
                    log.error("Copy resource to target <{}> failed", dest.getAbsolutePath());
                }
                try {
                    is.close();
                } catch (IOException ioex) {
                    log.warn("Error in closing input stream", ioex);
                }
            } else {
                log.error("Could not find m3u8Client platform executable in resources for <{}>", resourceName);
            }
        } catch (NullPointerException ex) {
            log.error(
                    "Could not find m3u8Client executable for {} is the correct platform jar included?",
                    resourceName);
            throw ex;
        }
    }

    private boolean copy(InputStream source, String destination) {
        boolean success = true;
        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            log.error("Cannot write file " + destination, ex);
            success = false;
        }
        return success;
    }

    private String clientName(String arch, String suffix) {
        return "N_m3u8DL-RE_" + VERSION + "_" + arch + "_" + suffix;
    }
}
