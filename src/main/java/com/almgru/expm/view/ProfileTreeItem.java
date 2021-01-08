package com.almgru.expm.view;

import com.almgru.expm.model.Profile;

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
