package system.live.livechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import system.live.livechat.domain.ChatMessage;
import system.live.livechat.repository.ChatMessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ChatHistoryController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    private static LocalDateTime firstConnectionTime;

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory() {
        if (firstConnectionTime == null) {
            firstConnectionTime = LocalDateTime.now();  
        }

        return chatMessageRepository.findByTimestampAfter(firstConnectionTime);
    }
}
