package com.nowcent.player;

import com.nowcent.pojo.Card;
import com.nowcent.service.DeckOfCards;
import com.nowcent.utils.GameRuler;
import com.nowcent.utils.GameUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nowcent.utils.GameRuler.evaluate;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/20 22:54
 */
public abstract class Player {
    protected Card[] cards;
    protected GameRuler.CardScore cardScore;
    private int winCount = 0;

    public void winCountIncrement(){
        winCount++;
    }

    public int getWinCount(){
        return winCount;
    }

    /**
     * 最大替换牌数
     */
    protected final int MAX_REPLACE_CARDS_NUMBER = 3;


    /**
     * 优化卡牌分数，从牌堆抽牌以替换
     * @param players 全部玩家数组
     * @param currentPlayerIndex 当前玩家在数组里的索引
     */
    public abstract void optimizeCards(Player[] players, int currentPlayerIndex);

    public void changeCardFromOthers(Player targetPlayer, int[] targetCardIndex, int[] originCardIndex) {
        targetPlayer.passiveChangeCardToOthers(targetCardIndex, this, originCardIndex);
        this.cardScore = evaluate(cards);
    }

    public void passiveChangeCardToOthers(int[] targetCardIndexes, Player originPlayer, int[] originCardIndexes) {
        try {
            Card[] originCard = originPlayer.getCards(originCardIndexes);
            Card[] targetCard = this.getCards(targetCardIndexes);
            originPlayer.setCards(targetCard, originCardIndexes);
            this.setCards(originCard, targetCardIndexes);
            this.cardScore = evaluate(cards);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
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

    public Card[] getCards(int[] indexes) throws CloneNotSupportedException {
        Set<Integer> set = new HashSet<>();
        Arrays.stream(indexes).forEach(set::add);
        if(set.size() != indexes.length){
            throw new RuntimeException("替换卡片数组长度与替换卡牌索引长度不一致");
        }

        Card[] cards = new Card[indexes.length];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = (Card) this.cards[indexes[i]].clone();
        }
        return cards;
    }

    public void setCards(Card[] cards, int[] indexes) throws CloneNotSupportedException {
        if(indexes.length != cards.length){
            throw new RuntimeException("替换卡片数组长度与替换卡牌索引长度不一致");
        }

        Set<Integer> set = new HashSet<>();
        Arrays.stream(indexes).forEach(set::add);
        if(set.size() != indexes.length){
            throw new RuntimeException("替换卡片数组长度与替换卡牌索引长度不一致");
        }

        for (int i = 0; i < cards.length; i++) {
            this.cards[indexes[i]] = cards[i];
        }
    }

}
