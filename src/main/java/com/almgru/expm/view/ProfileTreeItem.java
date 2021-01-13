package com.almgru.expm.view;

import com.almgru.expm.model.Profile;

/**
 * Temporary wrapper to allow both Templates and Profiles to be stored in a
 * TreeItem.
 * <p>
 * TODO: Revisit this when Template class is added.
 */
public class ProfileTreeItem {
    public final Profile profile;
    private final String description;

    public ProfileTreeItem(Profile profile) {
        this.profile = profile;
        this.description = profile.toString();
    }

    public ProfileTreeItem(String description) {
        this.profile = null;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
