package com.almgru.expm.system;

import java.io.File;
import java.io.IOException;

/**
 * Responsible for starting Firefox.
 * <p>
 * Requires a ProcessLauncher and the path to the instance of Firefox to
 * launch.
 */
public class FirefoxLauncher {
    private final ProcessLauncher processLauncher;
    private final File firefoxInstallPath;

    public FirefoxLauncher(
            ProcessLauncher processLauncher,
            File firefoxInstallPath
    ) {
        this.processLauncher = processLauncher;
        this.firefoxInstallPath = firefoxInstallPath;
    }

    /**
     * Launch Firefox with specified parameters.
     *
     * @param arguments Command line arguments to start Firefox with.
     *
     * @throws IOException if launching Firefox fails.
     */
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
