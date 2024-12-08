package system.live.livechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import system.live.livechat.domain.ChatInput;
import system.live.livechat.domain.ChatMessage;
import system.live.livechat.domain.ChatOutput;
import system.live.livechat.repository.ChatMessageRepository;

import java.time.LocalDateTime;

@Controller
public class LiveChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @MessageMapping("/new-message")
    @SendTo("/topics/livechat")
    public ChatOutput newMessage(ChatInput input) {
        
        if (input.message().trim().isEmpty()) {
            return null;  // Ou uma resposta vazia, conforme sua lógica
        }
    
        String escapedMessage = HtmlUtils.htmlEscape(input.user() + ": " + input.message());
    
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(input.user());
        chatMessage.setMessage(input.message());
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);
    
        return new ChatOutput(escapedMessage);
    }
}
