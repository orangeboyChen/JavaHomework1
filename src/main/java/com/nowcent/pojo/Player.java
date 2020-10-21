package com.nowcent.pojo;

import com.nowcent.service.DeckOfCards;
import com.nowcent.utils.GameUtils;

import static com.nowcent.utils.GameRuler.evaluate;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/20 22:54
 */
public abstract class Player {
    protected Card[] cards;
    protected GameUtils.CardScore cardScore;

    public abstract void optimizeCards(DeckOfCards deckOfCards);

    public GameUtils.CardScore getCardScore() {
        return cardScore;
    }

    public void setCardScore(GameUtils.CardScore cardScore) {
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
