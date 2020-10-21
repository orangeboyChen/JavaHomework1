package com.nowcent.pojo;

import com.nowcent.service.DeckOfCards;
import com.nowcent.utils.GameUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static com.nowcent.utils.GameRuler.evaluate;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/20 22:55
 */
public class RealPlayer extends Player {
    private final Scanner scanner = new Scanner(System.in);
    private final Input input;

    public interface Input{
        int[] replaceCardIndex();
    }

    public RealPlayer(Input inputInterface){
        input = inputInterface;
    }

    public RealPlayer(){
        input = () -> {
            Set<Integer> integerSet = new HashSet<>();
            int nextInt;
            System.out.println("输入想替换的牌（1，2，3，4，5），输入9结束");
            while((nextInt = scanner.nextInt()) < 9){
                integerSet.add(nextInt);
            }
            if(integerSet.size() == 0){
                return null;
            }
            return Arrays.stream(integerSet.toArray(new Integer[1])).mapToInt(Integer::intValue).toArray();
        };
    }

    @Override
    public void optimizeCards(DeckOfCards deckOfCards) {
        int[] replaceCardIndex = input.replaceCardIndex();
        if(replaceCardIndex == null){
            System.out.println("你选择了跳过");
            return;
        }
        if(replaceCardIndex.length > 3){
            throw new RuntimeException("数组长度大于3");
        }

        for (int cardIndex : replaceCardIndex) {
            cards[cardIndex - 1] = deckOfCards.dealCard();
        }
        cardScore = evaluate(cards);
        System.out.printf("替换后，你的牌是%s（%s）\n", Arrays.toString(cards), cardScore);
        System.out.println("===");

    }

}
