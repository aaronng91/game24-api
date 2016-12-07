package game24;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

    private static final int MAX = 13;
    private static final int MIN = 1;

    private int[] cards;

    @RequestMapping("/cards")
    @CrossOrigin()
    public ResponseEntity<int[]> getCards() {
        if (cards == null) {
            cards = new int[4];
            generateRandomCards();
        }
        return ResponseEntity.ok().body(cards);
    }

    @MessageMapping("/refresh")
    @SendTo("/topic/cards")
    public int[] cards() {
        generateRandomCards();
        return cards;
    }

    private void generateRandomCards() {
        for (int i = 0; i < 4; i ++) {
            cards[i] = (int) Math.floor(Math.random() * (MAX - MIN + 1)) + MIN;
        }
    }
}
