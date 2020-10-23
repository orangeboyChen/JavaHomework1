package com.nowcent.service.impl;

import com.nowcent.pojo.Card;
import com.nowcent.service.DeckOfCards;

import java.util.*;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/15 23:37
 */
public class DeckOfCardsImpl implements DeckOfCards {
    private Card[] deck = new Card[NUMBER_OF_CARDS];
    private int currentCard;
    private static final int NUMBER_OF_CARDS = 52;
    private static final Random randomNumbers = new Random();



//    private final String[] faces = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
//    private final String[] suits = { "红桃", "方块", "梅花", "黑桃" };


//    private final String[] faces = { "Ace", "Deuce", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King" };
//    private final String[] suits = { "Hearts", "Diamonds", "Clubs", "Spades" };

    /**
     * 单例模式
     */
    private static final DeckOfCardsImpl deckOfCards = new DeckOfCardsImpl();

    private DeckOfCardsImpl() {
        for ( int count = 0; count < deck.length; count++ ){
            deck[count] = new Card(Card.Face.class.getEnumConstants()[count % 13], Card.Suit.class.getEnumConstants()[count / 13]);
        }
        currentCard = 0;
    }

    public static DeckOfCardsImpl getDeckOfCards() {
        return deckOfCards;
    }

    @Override
    public void shuffle() {
        currentCard = 0;
        for ( int first = 0; first < deck.length; first++ ) {
            int second = randomNumbers.nextInt(NUMBER_OF_CARDS);
            Card temp = deck[first];
            deck[first] = deck[second];
            deck[second] = temp;
        }
    }

    @Override
    public Card dealCard() {
        if(currentCard < deck.length) {
            return deck[currentCard++];
        } else {
            return null;
        }
    }

}
