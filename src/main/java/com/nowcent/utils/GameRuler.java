package com.nowcent.utils;

import com.nowcent.pojo.Card;

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
    public static boolean isSinglePair(Card[] cards) {
        for (Integer integer : cardFaceArrayToMap(cards).values()) {
            if (integer >= 2) {
                return true;
            }
        }
        return false;
    }

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

    public static boolean isThreeOfAKind(Card[] cards) {
        for (Integer integer : cardFaceArrayToMap(cards).values()) {
            if (integer >= 3) {
                return true;
            }
        }
        return false;

    }

    public static boolean isFourOfAKind(Card[] cards) {
        for (Integer integer : cardFaceArrayToMap(cards).values()) {
            if (integer >= 4) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAFlush(Card[] cards) {
        for(Integer integer : cardSuitArrayToMap(cards).values()){
            if(integer >= 5){
                return true;
            }
        }
        return false;
    }

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



    public static GameUtils.CardScore evaluate(Card[] cards){
        if(isAFlush(cards)){
            return GameUtils.CardScore.FLUSH;
        }
        else if(isFourOfAKind(cards)){
            return GameUtils.CardScore.FOUR_OF_A_KIND;
        }
        else if(isAFullHouse(cards)){
            return GameUtils.CardScore.FULL_HOUSE;
        }
        else if(isStraight(cards)){
            return GameUtils.CardScore.STRAIGHT;
        }
        else if(isThreeOfAKind(cards)){
            return GameUtils.CardScore.THREE_OF_A_KIND;
        }
        else if(isTwoPairs(cards)){
            return GameUtils.CardScore.TWO_PAIR;
        }
        else if(isSinglePair(cards)){
            return GameUtils.CardScore.ONE_PAIR;
        }
        else{
            return GameUtils.CardScore.HIGH;
        }
    }

}
