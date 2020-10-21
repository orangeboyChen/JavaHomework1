package com.nowcent.service;

import com.nowcent.pojo.Card;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/15 23:45
 */
public interface DeckOfCards {
    /**
     * 洗牌
     */
    void shuffle();

    /**
     * 看下一张牌
     * @return 下一张牌
     */
    Card dealCard();

    enum Face{
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
        Face(int index, String face){
            this.index = index;
            this.face = face;
        }

        public int getIndex() {
            return index;
        }

        public String getFace() {
            return face;
        }
    }

    enum Suit{
        红桃(0),
        方块(1),
        梅花(2),
        黑桃(3);

        private int index;
        Suit(int index){
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }


}
