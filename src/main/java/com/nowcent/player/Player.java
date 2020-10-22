package com.nowcent.player;

import com.nowcent.pojo.Card;
import com.nowcent.utils.GameRuler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
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

    public final static int CHANGE_CARD_TYPE_DEFAULT = 0;
    public final static int CHANGE_CARD_TYPE_SINGLE = 1;
    public final static int CHANGE_CARD_TYPE_ONCE = 2;

    private static int changeCardType = CHANGE_CARD_TYPE_DEFAULT;



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

    /**
     * 主动换卡
     * @param targetPlayer 被换卡方
     * @param targetCardIndexes 被换卡方的索引
     * @param originCardIndexes 换卡方的索引
     */
    public void changeCard(Player targetPlayer, int[] targetCardIndexes, int[] originCardIndexes) {
        targetPlayer.passiveChangeCard(targetCardIndexes, this, originCardIndexes);
        this.cardScore = evaluate(cards);
    }

    /**
     * 被动换卡
     * @param targetCardIndexes 被换卡方的索引
     * @param originPlayer 要换卡的索引
     * @param originCardIndexes 换卡方的索引
     */
    public void passiveChangeCard(int[] targetCardIndexes, Player originPlayer, int[] originCardIndexes) {
        try {
            Card[] originCards = originPlayer.getCards(originCardIndexes);
            Card[] targetCards = this.getCards(targetCardIndexes);
            originPlayer.setCards(targetCards, originCardIndexes);
            this.setCards(originCards, targetCardIndexes);
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

    /**
     * 获取全部卡片
     * @return 全部卡片
     */
    public Card[] getCards() {
        return cards;
    }

    /**
     * 从索引获得卡片
     * @param indexes 索引数组
     * @return 卡片
     * @throws CloneNotSupportedException 卡片无法克隆错误
     */
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

    /**
     * 设置部分卡片
     * 注意：卡片数组索引与索引数组索引一一对应
     * @param cards 要设置的卡片
     * @param indexes 要设置的索引
     */
    public void setCards(Card[] cards, int[] indexes) {
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

    public static void setChangeCardType(int changeCardType) {
        Player.changeCardType = changeCardType;
    }
}
