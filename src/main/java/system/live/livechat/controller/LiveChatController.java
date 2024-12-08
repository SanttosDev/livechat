package system.live.livechat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import system.live.livechat.domain.ChatInput;
import system.live.livechat.domain.ChatOutput;

@Controller
public class LiveChatController {

    @MessageMapping("/new-message")
    @SendTo("/topics/livechat")
    public ChatOutput newMessage(ChatInput input) {
        String escapedMessage = HtmlUtils.htmlEscape(input.user() + ": " + input.message());
        return new ChatOutput(escapedMessage);
    }

}
