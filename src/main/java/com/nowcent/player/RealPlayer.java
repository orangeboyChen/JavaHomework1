package com.nowcent.player;

import com.nowcent.service.DeckOfCards;

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

    /**
     * 结束替换牌的数字
     */
    private final int EXIT_NUMBER = 9;


    public interface Input{
        /**
         * 真实玩家输入替换牌的index
         * @return 替换牌index
         */
        int[] replaceCardIndex();
    }

    public RealPlayer(Input inputInterface){
        input = inputInterface;
    }

    public RealPlayer(){
        //若选择无参构造，则input接口为默认
        input = () -> {
            Set<Integer> integerSet = new HashSet<>();
            int nextInt;
            System.out.println("输入想替换的牌（1，2，3，4，5），输入9结束");
            while((nextInt = scanner.nextInt()) < EXIT_NUMBER){
                integerSet.add(nextInt);
            }

            //啥都没输，返回0
            if(integerSet.size() == 0){
                return null;
            }
            return Arrays.stream(integerSet.toArray(new Integer[1])).mapToInt(Integer::intValue).toArray();
        };
    }

    @Override
    public void optimizeCards(DeckOfCards deckOfCards) {
        int[] replaceCardIndex = input.replaceCardIndex();

        //啥都没输，就是跳过
        if(replaceCardIndex == null){
            System.out.println("你选择了跳过");
            return;
        }

        //检测非法输入
        if(replaceCardIndex.length > MAX_REPLACE_CARDS_NUMBER){
            throw new RuntimeException("数组长度大于3");
        }

        //抽卡
        for (int cardIndex : replaceCardIndex) {
            cards[cardIndex - 1] = deckOfCards.dealCard();
        }

        //重新评估分数
        cardScore = evaluate(cards);

        //控制台展示
        System.out.printf("替换后，你的牌是%s（%s）\n", Arrays.toString(cards), cardScore);
        System.out.println("===");

    }

}
