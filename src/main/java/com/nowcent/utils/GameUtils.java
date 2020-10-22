package com.nowcent.utils;

import com.nowcent.pojo.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/20 21:59
 */
public class GameUtils {

    /**
     * 把卡牌的点数转化为Map，value为牌的数量
     * @param cards 卡牌
     * @return Map
     */
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

    /**
     * 把卡牌的花色转化为Map，value为牌的数量
     * @param cards 卡牌
     * @return Map
     */
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
