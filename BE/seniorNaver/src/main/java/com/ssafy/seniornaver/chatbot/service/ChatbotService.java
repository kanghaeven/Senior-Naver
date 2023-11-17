package com.ssafy.seniornaver.chatbot.service;

import com.ssafy.seniornaver.auth.entity.Member;
import org.springframework.web.multipart.MultipartFile;

public interface ChatbotService {
    String convertSpeechToText(MultipartFile voiceFile);

    String talkToChatbot(String text, Member member);

    byte[] convertTextToSpeech(String response);

    String talkToChat(String text,Member member);
}
