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
    /**
     * 点数
     */
    private DeckOfCards.Face face;

    /**
     * 花色
     */
    private DeckOfCards.Suit suit;

    @Override
    public String toString() {
        return suit.toString() + face.getFace();
    }
}
