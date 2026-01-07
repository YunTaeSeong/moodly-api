package com.moodly.user.mail;

public interface EmailSender {
    void sendFindIdCode(String email, String code);
    void sendPasswordResetLink(String email, String link);
}
