package com.almgru.expm.observers;

import com.almgru.expm.model.Profile;

/**
 * Interface for observers listening to user input events on the main window.
 */
public interface MainWindowObserver {
    /**
     * Called when the user double-clicks on a profile.
     *
     * @param profile The profile that was double-clicked on
     */
    void onProfileDoubleClicked(Profile profile);
}
