package com.almgru.expm.model;

/**
 * Simple POJO for profiles.
 * <p>
 * Stores the profile's sequence number, name and path.
 */
public class Profile implements Comparable<Profile> {
    public final int sequence;
    public final String name;
    public final boolean isRelative;
    public final String path;

    public Profile(int sequence, String name, boolean isRelative, String path) {
        this.sequence = sequence;
        this.name = name;
        this.isRelative = isRelative;
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Profile)) {
            return false;
        }

        Profile other = (Profile) obj;

        return this.sequence == other.sequence
                && this.name.equals(other.name)
                && this.isRelative == other.isRelative
                && this.path.equals(other.path);
    }

    @Override
    public String toString() {
        return String.format(
                "Profile #%d: %s",
                this.sequence + 1,
                this.name
        );
    }

    @Override
    public int compareTo(Profile o) {
        return Integer.compare(this.sequence, o.sequence);
    }
}
