package game24.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @SubscribeMapping("/topic/playerId")
    public int getPlayerId(SimpMessageHeaderAccessor headerAccessor) {
        return playerService.getPlayerId(headerAccessor.getSessionId());
    }
}