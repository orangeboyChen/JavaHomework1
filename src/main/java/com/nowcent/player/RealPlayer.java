package com.nowcent.player;

import com.nowcent.pojo.Card;

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
    private final Input input;

    /**
     * 结束替换牌的数字
     */
    private final int EXIT_NUMBER = 9;


    public interface Input{
        Scanner scanner = new Scanner(System.in);

        /**
         * 真实玩家输入替换牌的index
         * @return 替换牌index
         */
        int[] getReplaceTargetCardIndex();

        /**
         * 真实玩家输入替换对方牌的index
         * @return 替换牌index
         */
        int[] getReplaceOriginCardIndex();

        /**
         * 真实玩家输入替换的对方
         * @return 替换对方index
         */
        int getReplaceCardPlayerIndex(int currentPlayerIndex);
    }

    public RealPlayer(Input inputInterface){
        input = inputInterface;
    }

    public RealPlayer(){
        //若选择无参构造，则input接口为默认
        input = new Input() {
            @Override
            public int[] getReplaceTargetCardIndex() {
                Set<Integer> integerSet = new HashSet<>();
                int nextInt;
                System.out.println("\n对方的牌是[***, ***, ***, ***, ***]");
                System.out.println("输入想替换对方的牌（1，2，3，4，5），输入9结束");
                while((nextInt = scanner.nextInt()) < EXIT_NUMBER){
                    integerSet.add(nextInt - 1);
                }

                //啥都没输，返回0
                if(integerSet.size() == 0){
                    return null;
                }
                return Arrays.stream(integerSet.toArray(new Integer[1])).mapToInt(Integer::intValue).toArray();
            }

            @Override
            public int[] getReplaceOriginCardIndex() {
                Set<Integer> integerSet = new HashSet<>();
                int nextInt;
                System.out.println("输入想替换自己的牌（1，2，3，4，5），输入9结束");
                while((nextInt = scanner.nextInt()) < EXIT_NUMBER){
                    integerSet.add(nextInt - 1);
                }

                //啥都没输，返回0
                if(integerSet.size() == 0){
                    return null;
                }
                return Arrays.stream(integerSet.toArray(new Integer[1])).mapToInt(Integer::intValue).toArray();
            }

            @Override
            public int getReplaceCardPlayerIndex(int currentPlayerIndex) {
                System.out.print("\n输入要替换牌玩家的编号（输入0跳过）：");
                int targetIndex = scanner.nextInt();
                if(targetIndex > 0){
                    return targetIndex;
                }
                return -1;
            }
        };
    }


    @Override
    public void optimizeCards(Player[] players, int currentPlayerIndex) {
        //遍历输出全部玩家
        System.out.print("全部玩家为：");

        for (int i = 0; i < players.length; i++) {
            if (players[i] instanceof RobotPlayer) {
                System.out.print("玩家" + ((RobotPlayer) players[i]).getIndex() + " ");
            }
        }

        //遍历找到用户想要的玩家
        int replaceCardPlayerIndex = input.getReplaceCardPlayerIndex(currentPlayerIndex);
        Player targetPlayer = null;

        //啥都没输，就是跳过
        if(replaceCardPlayerIndex == -1){
            System.out.println("你选择了跳过");
            return;
        }


        for (Player player : players) {
            if (player instanceof RobotPlayer && ((RobotPlayer) player).getIndex() == replaceCardPlayerIndex) {
                targetPlayer = player;
            }
        }

        //非法输入
        if(targetPlayer == null){
            throw new RuntimeException("玩家编号错误");
        }


        int[] replaceOriginCardIndex = input.getReplaceOriginCardIndex();
        //啥都没输，就是跳过
        if(replaceOriginCardIndex == null){
            System.out.println("你选择了跳过");
            return;
        }

        int[] replaceTargetCardIndex = input.getReplaceTargetCardIndex();
        if(replaceTargetCardIndex == null){
            System.out.println("你选择了跳过");
            return;
        }

        //检测非法输入
        if(replaceTargetCardIndex.length != replaceOriginCardIndex.length){
            throw new RuntimeException("两次输入长度不一致！");
        }

        if(replaceTargetCardIndex.length > MAX_REPLACE_CARDS_NUMBER){
            throw new RuntimeException("数组长度大于3");
        }

        //抽卡
        changeCard(targetPlayer, replaceTargetCardIndex, replaceOriginCardIndex);

        //重新评估分数
        cardScore = evaluate(cards);

        //控制台展示
        System.out.printf("替换后，你的牌是%s（%s）\n", Arrays.toString(cards), cardScore);
    }

    @Override
    public void passiveChangeCard(int[] targetCardIndexes, Player originPlayer, int[] originCardIndexes) {
        Card[] changeCards = new Card[targetCardIndexes.length];
        for (int i = 0; i < changeCards.length; i++) {
            changeCards[i] = cards[targetCardIndexes[i]];
        }
        System.out.println("玩家" + ((RobotPlayer)originPlayer).getIndex() + "抽走了你的" + Arrays.toString(changeCards) + "（" + originCardIndexes.length + "张）。");

        super.passiveChangeCard(targetCardIndexes, originPlayer, originCardIndexes);
        System.out.println("你当前的牌是" + Arrays.toString(cards) + "（" + cardScore + "）");

    }
}
