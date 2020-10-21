package com.nowcent.pojo;

import com.nowcent.service.DeckOfCards;
import com.nowcent.utils.GameUtils;

import java.util.*;

import static com.nowcent.utils.GameRuler.evaluate;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/21 8:31
 */
public class RobotPlayer extends Player {
    @Override
    public void optimizeCards(DeckOfCards deckOfCards) {
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

    private void optimizeHighCards(DeckOfCards deckOfCards){
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
