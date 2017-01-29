package game24.tap;

import game24.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TapController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TapService tapService;

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/gotIt")
    public void gotIt(SimpMessageHeaderAccessor headerAccessor) {
        if (!tapService.isEndOfRound()) {
            tapService.endRound();

            template.convertAndSend("/topic/gotIt",
                    playerService.getPlayerId(headerAccessor.getSessionId()));
        }
    }
}
