package com.nowcent.utils;

import com.nowcent.pojo.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nowcent.utils.GameUtils.cardFaceArrayToMap;
import static com.nowcent.utils.GameUtils.cardSuitArrayToMap;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/21 12:53
 */
public class GameRuler {
    /**
     * 牌的形式
     */
    @Getter
    @AllArgsConstructor
    public enum CardScore{
        HIGH(0),
        ONE_PAIR(1),
        TWO_PAIR(2),
        THREE_OF_A_KIND(3),
        STRAIGHT(4),
        FULL_HOUSE(5),
        FOUR_OF_A_KIND(6),
        FLUSH(7);

        private int index;
    }

    /**
     * 判断是否是一对
     * @param cards 要判断的牌
     * @return 结果
     */
    public static boolean isSinglePair(Card[] cards) {
        for (Integer integer : cardFaceArrayToMap(cards).values()) {
            if (integer >= 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是两对
     * @param cards 要判断的牌
     * @return 结果
     */
    public static boolean isTwoPairs(Card[] cards) {
        int pairsCount = 0;
        for (Integer integer : cardFaceArrayToMap(cards).values()) {
            if (integer >= 2) {
                pairsCount++;
            }
            if(integer >= 4){
                pairsCount++;
            }
            if(integer >= 6){
                pairsCount++;
            }
        }
        return pairsCount >= 2;
    }

    /**
     * 判断是否是三条
     * @param cards 要判断的牌
     * @return 结果
     */
    public static boolean isThreeOfAKind(Card[] cards) {
        for (Integer integer : cardFaceArrayToMap(cards).values()) {
            if (integer >= 3) {
                return true;
            }
        }
        return false;

    }

    /**
     * 判断是否是四条
     * @param cards 要判断的牌
     * @return 结果
     */
    public static boolean isFourOfAKind(Card[] cards) {
        for (Integer integer : cardFaceArrayToMap(cards).values()) {
            if (integer >= 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是同花
     * @param cards 要判断的牌
     * @return 结果
     */
    public static boolean isAFlush(Card[] cards) {
        for(Integer integer : cardSuitArrayToMap(cards).values()){
            if(integer >= 5){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是顺子
     * @param cards 要判断的牌
     * @return 结果
     */
    public static boolean isStraight(Card[] cards) {
        Card[] sortedCards = Arrays.stream(cards).sorted(Comparator.comparing(card -> card.getFace().getIndex())).collect(Collectors.toList()).toArray(new Card[1]);
        for (int i = 0; i < sortedCards.length - 4; i++) {
            boolean flag = true;
            for (int j = 0; j < 4; j++) {
                if (sortedCards[i + j + 1].getFace().getIndex() - sortedCards[i + j].getFace().getIndex() != 1) {
                    flag = false;
                    break;
                }
            }
            if(flag){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是葫芦
     * @param cards 要判断的牌
     * @return 结果
     */
    public static boolean isAFullHouse(Card[] cards) {
        Map<String, Integer> map = cardFaceArrayToMap(cards);
        int flag = 0;

        for (Integer value : map.values()) {
            if(value >= 2){
                flag += value;
            }
        }
        return flag >= 5;
    }

    /**
     * 评估牌的质量
     * @param cards 卡牌
     * @return 牌形式枚举
     */
    public static CardScore evaluate(Card[] cards){
        if(isFourOfAKind(cards)){
            return CardScore.FOUR_OF_A_KIND;
        }
        else if(isAFullHouse(cards)){
            return CardScore.FULL_HOUSE;
        }
        else if(isAFlush(cards)){
            return CardScore.FLUSH;
        }
        else if(isStraight(cards)){
            return CardScore.STRAIGHT;
        }
        else if(isThreeOfAKind(cards)){
            return CardScore.THREE_OF_A_KIND;
        }
        else if(isTwoPairs(cards)){
            return CardScore.TWO_PAIR;
        }
        else if(isSinglePair(cards)){
            return CardScore.ONE_PAIR;
        }
        else{
            return CardScore.HIGH;
        }
    }

}
