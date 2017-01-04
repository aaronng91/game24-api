package game24;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

    private static final int MAX = 13;
    private static final int MIN = 1;

    private int[] cards = initialiseCards();

    @SubscribeMapping("/topic/cards")
    public int[] getCards() {
        return cards;
    }

    @MessageMapping("/refresh")
    @SendTo("/topic/cards")
    public int[] refreshCards() {
        cards = generateRandomCards(cards);
        return cards;
    }

    private int[] initialiseCards() {
        int[] initialCards = new int[4];
        return generateRandomCards(initialCards);
    }

    private int[] generateRandomCards(int[] cards) {
        for (int i = 0; i < 4; i ++) {
            cards[i] = (int) Math.floor(Math.random() * (MAX - MIN + 1)) + MIN;
        }
        return cards;
    }
}
