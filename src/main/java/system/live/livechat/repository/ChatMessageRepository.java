package system.live.livechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.live.livechat.domain.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByTimestampAfter(LocalDateTime timestamp);
}
