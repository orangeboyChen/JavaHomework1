package com.nowcent.utils;

import com.nowcent.pojo.Card;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/20 21:59
 */
public class GameUtils {

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
        CardScore(int index){
            this.index = index;
        }
        public int getIndex() {
            return index;
        }
    }

    public static Map<String, Integer> cardFaceArrayToMap(Card[] cards){
        Map<String, Integer> map = new HashMap<>(cards.length);
        for (Card card : cards) {
            if (map.containsKey(card.getFace().getFace())) {
                map.put(card.getFace().getFace(), map.get(card.getFace().getFace()) + 1);
            } else {
                map.put(card.getFace().getFace(), 1);
            }
        }
        return map;
    }

    public static Map<String, Integer> cardSuitArrayToMap(Card[] cards){
        Map<String, Integer> map = new HashMap<>(cards.length);
        for (Card card : cards) {
            if (map.containsKey(card.getSuit().toString())) {
                map.put(card.getSuit().toString(), map.get(card.getSuit().toString()) + 1);
            } else {
                map.put(card.getSuit().toString(), 1);
            }
        }
        return map;
    }


}
