package com.moodly.common.util;

public final class EmailMasking {

    public static String mask(String email) {
        if(email == null || !email.contains("@")) return null;
        String[] split = email.split("@", 2);
        String local = split[0];
        String domain = split[1];
        
        if(local.length() <= 2) {
            return local.charAt(0) + "*" + "@" + domain;
        }

        String prefix = local.substring(0, 2);
        String mask = "*".repeat(local.length() - 2);

        return prefix + mask + "@" + domain;
    }
}
