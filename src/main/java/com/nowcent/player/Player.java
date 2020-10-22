package com.nowcent.player;

import com.nowcent.pojo.Card;
import com.nowcent.service.DeckOfCards;
import com.nowcent.utils.GameRuler;
import com.nowcent.utils.GameUtils;

import static com.nowcent.utils.GameRuler.evaluate;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/20 22:54
 */
public abstract class Player {
    protected Card[] cards;
    protected GameRuler.CardScore cardScore;

    /**
     * 最大替换牌数
     */
    protected final int MAX_REPLACE_CARDS_NUMBER = 3;


    /**
     * 优化卡牌分数，从牌堆抽牌以替换
     * @param deckOfCards 牌堆
     */
    public abstract void optimizeCards(DeckOfCards deckOfCards);

    public GameRuler.CardScore getCardScore() {
        return cardScore;
    }

    public void setCardScore(GameRuler.CardScore cardScore) {
        this.cardScore = cardScore;
    }

    public void setCards(Card[] cards){
        this.cards = cards;
        this.cardScore = evaluate(cards);

    }

    public Card[] getCards() {
        return cards;
    }

}
