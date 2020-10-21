package com.nowcent.pojo;

import com.nowcent.service.DeckOfCards;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/15 23:33
 */
@Data
@AllArgsConstructor
public class Card {
    private DeckOfCards.Face face;
    private DeckOfCards.Suit suit;

    @Override
    public String toString() {
        return suit.toString() + face.getFace();
    }
}
