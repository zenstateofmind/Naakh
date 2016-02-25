package com.example.nikhiljoshi.naakh.user;

/**
 * This will a POJO for a user. This object will contain information such as the
 * languages that the user is proficient in, the name of the user, and maybe some other information
 *
 * Maybe this might be needed to be broken up and the info might be required to be stored in
 * SharedPreferences. Investigate!
 */
public class UserProfile {

    private final String username;
    private final long phone_number;
    private final String[] languages;

    public UserProfile(String username, long phone_number, String[] languages) {
        this.username = username;
        this.phone_number = phone_number;
        this.languages = languages;
    }

    public String getUsername() {
        return username;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public String[] getLanguages() {
        return languages;
    }
}
