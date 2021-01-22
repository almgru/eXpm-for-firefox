package com.almgru.expm.system;

import java.io.File;
import java.io.IOException;

class FirefoxLauncher {
    private final File firefoxInstallPath;

    FirefoxLauncher(File firefoxInstallPath) {
        this.firefoxInstallPath = firefoxInstallPath;
    }

    void launchFirefox(String ...arguments) throws IOException {
        Runtime.getRuntime().exec(addFirefoxExePathAtStartOfArgArray(arguments));
    }

    private String[] addFirefoxExePathAtStartOfArgArray(String[] argArray) {
        String[] firefoxArgs = new String[argArray.length];
        firefoxArgs[0] = this.firefoxInstallPath.getPath();

        for (int i = 0; i < argArray.length; i++) {
            firefoxArgs[i + 1] = argArray[i];
        }

        return firefoxArgs;
    }
}
