package com.almgru.expm.system;

import java.io.File;
import java.io.IOException;

public class FirefoxLauncher {
    private final ProcessLauncher processLauncher;
    private final File firefoxInstallPath;

    public FirefoxLauncher(ProcessLauncher processLauncher,
            File firefoxInstallPath) {
        this.processLauncher = processLauncher;
        this.firefoxInstallPath = firefoxInstallPath;
    }

    public void launchFirefox(String... arguments) throws IOException {
        processLauncher.launchProcess(addFirefoxExePathAtStartOfArgArray(arguments));
    }

    private String[] addFirefoxExePathAtStartOfArgArray(String[] argArray) {
        String[] firefoxArgs = new String[argArray.length + 1];
        firefoxArgs[0] = this.firefoxInstallPath.getPath();
        System.arraycopy(argArray, 0, firefoxArgs, 1, argArray.length);

        return firefoxArgs;
    }
}
