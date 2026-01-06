package com.moodly.user.mail;

public interface EmailSender {
    void sendFindIdCode(String email, String code);
}
