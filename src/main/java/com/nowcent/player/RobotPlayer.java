package com.nowcent.player;

import com.nowcent.service.DeckOfCards;
import com.nowcent.utils.GameRuler;
import com.nowcent.utils.GameUtils;

import java.util.*;

import static com.nowcent.utils.GameRuler.CardScore.*;
import static com.nowcent.utils.GameRuler.evaluate;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/21 8:31
 */
public class RobotPlayer extends Player {
    @Override
    public void optimizeCards(DeckOfCards deckOfCards) {
        //根据牌的形式，选择不同的优化方法
        switch(cardScore){
            case HIGH:
                optimizeHighCards(deckOfCards);
                break;
            case ONE_PAIR:
                optimizeOnePairCards(deckOfCards);
                break;
            case TWO_PAIR:
                optimizeTwoPairsCards(deckOfCards);
                break;
            case THREE_OF_A_KIND:
                optimizeThreeOfAKindCards(deckOfCards);
                break;
            case FOUR_OF_A_KIND:
                optimizeFourOfAKindCards(deckOfCards);
                break;
            default:
                break;
        }
        cardScore = evaluate(cards);

    }

    /**
     * 优化HIGH牌
     * 如果是HIGH牌，则随机抽掉3张
     * @param deckOfCards 牌堆
     */
    private void optimizeHighCards(DeckOfCards deckOfCards){
        //从0-4随机选3个数字
        Set<Integer> randomSet = new HashSet<>();
        while(randomSet.size() <= 3){
            randomSet.add(1 + new Random(System.currentTimeMillis()).nextInt(4));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Integer i : randomSet) {
            cards[i] = deckOfCards.dealCard();
        }
    }

    /**
     * 优化一对牌
     * 抽掉另外三张
     * @param deckOfCards 牌堆
     */
    private void optimizeOnePairCards(DeckOfCards deckOfCards){
        Map<String, Integer> cardFaceMap = GameUtils.cardFaceArrayToMap(cards);
        final String[] skipFace = new String[1];
        cardFaceMap.forEach((key, value) -> {
            if (value >= 2) {
                skipFace[0] = key;
            }
        });

        for (int i = 0; i < cards.length; i++) {
            if(!cards[i].getFace().getFace().equals(skipFace[0])){
                cards[i] = deckOfCards.dealCard();
            }
        }
    }

    /**
     * 优化两对牌
     * 抽掉最后一张异常牌
     * @param deckOfCards 牌堆
     */
    private void optimizeTwoPairsCards(DeckOfCards deckOfCards){
        Map<String, Integer> cardFaceMap = GameUtils.cardFaceArrayToMap(cards);
        final String[] skipFace = new String[1];
        cardFaceMap.forEach((key, value) -> {
            if (value <= 1) {
                skipFace[0] = key;
            }
        });

        for (int i = 0; i < cards.length; i++) {
            if(!cards[i].getFace().getFace().equals(skipFace[0])){
                cards[i] = deckOfCards.dealCard();
            }
        }

    }

    /**
     * 优化三条牌
     * 抽另外两张牌
     * @param deckOfCards 牌堆
     */
    private void optimizeThreeOfAKindCards(DeckOfCards deckOfCards){
        Map<String, Integer> cardFaceMap = GameUtils.cardFaceArrayToMap(cards);
        final String[] skipFace = new String[1];
        cardFaceMap.forEach((key, value) -> {
            if (value >= 3) {
                skipFace[0] = key;
            }
        });

        for (int i = 0; i < cards.length; i++) {
            if(!cards[i].getFace().getFace().equals(skipFace[0])){
                cards[i] = deckOfCards.dealCard();
            }
        }
    }

    /**
     * 优化四条牌
     * 抽掉最后一张牌
     * @param deckOfCards 牌堆
     */
    private void optimizeFourOfAKindCards(DeckOfCards deckOfCards){
        Map<String, Integer> cardFaceMap = GameUtils.cardFaceArrayToMap(cards);
        final String[] skipFace = new String[1];
        cardFaceMap.forEach((key, value) -> {
            if (value >= 4) {
                skipFace[0] = key;
            }
        });

        for (int i = 0; i < cards.length; i++) {
            if(!cards[i].getFace().getFace().equals(skipFace[0])){
                cards[i] = deckOfCards.dealCard();
            }
        }
    }


}
