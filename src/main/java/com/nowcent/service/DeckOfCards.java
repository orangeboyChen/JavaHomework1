package com.nowcent.service;

import com.nowcent.pojo.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;

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



}
