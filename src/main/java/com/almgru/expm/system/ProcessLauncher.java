package com.almgru.expm.system;

import java.io.IOException;

public class ProcessLauncher {
    public void launchProcess(String[] args) throws IOException {
        Runtime.getRuntime().exec(args);
    }
}
