package com.nowcent;

import com.nowcent.pojo.Card;
import com.nowcent.pojo.RealPlayer;
import com.nowcent.service.DeckOfCards;
import com.nowcent.service.GameService;
import com.nowcent.service.impl.DeckOfCardsImpl;
import com.nowcent.service.impl.GameServiceImpl;
import com.nowcent.utils.GameRuler;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/15 23:33
 */
public class Application {
    public static void main(String[] args) {
        DeckOfCards deckOfCards = DeckOfCardsImpl.getDeckOfCards();
        GameService gameService = new GameServiceImpl.GameServiceBuilder(deckOfCards, new RealPlayer())
                .playerTotal(3)
                .build();
        gameService.initGame();

    }
}
