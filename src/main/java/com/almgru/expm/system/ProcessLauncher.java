package com.almgru.expm.system;

import java.io.IOException;

/**
 * Responsible for launching processes.
 */
public class ProcessLauncher {
    /**
     * Launch program 'args'[0] with command line parameters 'args'[1..].
     *
     * @param args Should contain the process to launch at the first index, and
     *             the parameters to pass to the process in the rest of the
     *             array.
     *
     * @throws IOException If launching the process fails.
     */
    public void launchProcess(String[] args) throws IOException {
        Runtime.getRuntime().exec(args);
    }
}
