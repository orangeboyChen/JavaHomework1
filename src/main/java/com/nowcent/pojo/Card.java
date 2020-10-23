package com.nowcent.pojo;

import com.nowcent.service.DeckOfCards;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/15 23:33
 */
@Data
@AllArgsConstructor
public class Card implements Cloneable {

    /**
     * 卡牌点数
     */
    @AllArgsConstructor
    @Getter
    public enum Face{
        A(1, "A"),
        NUM2(2, "2"),
        NUM3(3, "3"),
        NUM4(4, "4"),
        NUM5(5, "5"),
        NUM6(6, "6"),
        NUM7(7, "7"),
        NUM8(8, "8"),
        NUM9(9, "9"),
        NUM10(10, "10"),
        J(11, "J"),
        Q(12, "Q"),
        K(13, "K");

        private int index;
        private String face;
    }

    /**
     * 卡牌花色
     */
    @AllArgsConstructor
    @Getter
    public enum Suit{
        红桃(0),
        方块(1),
        梅花(2),
        黑桃(3);

        private int index;
    }


    /**
     * 点数
     */
    private Face face;

    /**
     * 花色
     */
    private Suit suit;

    @Override
    public String toString() {
        return suit.toString() + face.getFace();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
