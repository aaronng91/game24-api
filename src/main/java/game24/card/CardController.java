package game24.card;

import game24.tap.TapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CardController {

    private static final int MAX = 13;
    private static final int MIN = 1;

    private int[] cards = initialiseCards();

    @Autowired
    private TapService tapService;

    @SubscribeMapping("/topic/cards")
    public int[] getCards() {
        return cards;
    }

    @MessageMapping("/refresh")
    @SendTo("/topic/cards")
    public int[] refreshCards() {
        tapService.restartGame();
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
