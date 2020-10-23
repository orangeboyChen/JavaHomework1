package com.nowcent;

import com.nowcent.player.RealPlayer;
import com.nowcent.service.DeckOfCards;
import com.nowcent.service.GameService;
import com.nowcent.service.impl.DeckOfCardsImpl;
import com.nowcent.service.impl.GameServiceImpl;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/15 23:33
 */
public class Application {
    public static void main(String[] args) {
        //创建牌堆
        DeckOfCards deckOfCards = DeckOfCardsImpl.getDeckOfCards();

        //创建游戏服务
        GameService gameService = new GameServiceImpl.GameServiceBuilder(deckOfCards, new RealPlayer())
                .robotPlayerTotal(9)
                .gameTotal(3)
                .changeCardType(GameServiceImpl.CHANGE_CARD_TYPE_DEFAULT)
                .build();

        //开始游戏
        gameService.play();

    }
}
