package xyz.hooy.cpw.core;

import java.util.Objects;

public class GeneralKiller extends Thread {

    private final Process process;

    public GeneralKiller(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        if (Objects.nonNull(process) && process.isAlive()) {
            process.destroy();
        }
    }
}
