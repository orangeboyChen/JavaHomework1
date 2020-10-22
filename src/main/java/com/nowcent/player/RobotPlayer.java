package com.nowcent.player;

import com.nowcent.utils.GameUtils;

import java.util.*;

import static com.nowcent.utils.GameRuler.evaluate;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/21 8:31
 */
public class RobotPlayer extends Player {
    private final int index;
    public RobotPlayer(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }

    @Override
    public void optimizeCards(Player[] players, int currentPlayerIndex) {
        //根据牌的形式，选择不同的优化方法
        switch(cardScore){
            case HIGH:
                optimizeHighCards(players, currentPlayerIndex);
                break;
            case ONE_PAIR:
                optimizeOnePairCards(players, currentPlayerIndex);
                break;
            case TWO_PAIR:
                optimizeTwoPairsCards(players, currentPlayerIndex);
                break;
            case THREE_OF_A_KIND:
                optimizeThreeOfAKindCards(players, currentPlayerIndex);
                break;
            case FOUR_OF_A_KIND:
                optimizeFourOfAKindCards(players, currentPlayerIndex);
                break;
            default:
                break;
        }
        cardScore = evaluate(cards);

    }


    /**
     * 优化HIGH牌
     * 如果是HIGH牌，则随机抽掉3张
     * @param players 全部玩家数组
     * @param currentPlayerIndex 当前玩家在数组里的索引
     */
    private void optimizeHighCards(Player[] players, int currentPlayerIndex){
        //从0-4随机选3个数字
        int[] originIndexes = getRandomIndexes(3);

        int[] targetIndexes = getRandomIndexes(3);

        //抽牌
        changeCard(getRandomPlayer(players, currentPlayerIndex), targetIndexes, originIndexes);
    }

    /**
     * 优化一对牌
     * 抽掉另外三张
     * @param players 全部玩家数组
     * @param currentPlayerIndex 当前玩家在数组里的索引
     */
    private void optimizeOnePairCards(Player[] players, int currentPlayerIndex){
        Map<String, Integer> cardFaceMap = GameUtils.cardFaceArrayToMap(cards);
        final String[] skipFace = new String[1];
        cardFaceMap.forEach((key, value) -> {
            if (value >= 2) {
                skipFace[0] = key;
            }
        });

        int[] originIndexes = getIndexesFromSkipFaces(skipFace[0]);

        //抽牌
        changeCard(getRandomPlayer(players, currentPlayerIndex), getRandomIndexes(originIndexes.length), originIndexes);


    }

    /**
     * 优化两对牌
     * 抽掉最后一张异常牌
     * @param players 全部玩家数组
     * @param currentPlayerIndex 当前玩家在数组里的索引
     */
    private void optimizeTwoPairsCards(Player[] players, int currentPlayerIndex){
        Map<String, Integer> cardFaceMap = GameUtils.cardFaceArrayToMap(cards);
        final String[] skipFace = new String[1];
        cardFaceMap.forEach((key, value) -> {
            if (value <= 1) {
                skipFace[0] = key;
            }
        });

        int[] originIndexes = getIndexesFromSkipFaces(skipFace[0]);

        //抽牌
        changeCard(getRandomPlayer(players, currentPlayerIndex), getRandomIndexes(originIndexes.length), originIndexes);

    }

    /**
     * 优化三条牌
     * 抽另外两张牌
     * @param players 全部玩家数组
     * @param currentPlayerIndex 当前玩家在数组里的索引
     */
    private void optimizeThreeOfAKindCards(Player[] players, int currentPlayerIndex){
        Map<String, Integer> cardFaceMap = GameUtils.cardFaceArrayToMap(cards);
        final String[] skipFace = new String[1];
        cardFaceMap.forEach((key, value) -> {
            if (value >= 3) {
                skipFace[0] = key;
            }
        });



        int[] originIndexes = getIndexesFromSkipFaces(skipFace[0]);

        //抽牌
        changeCard(getRandomPlayer(players, currentPlayerIndex), getRandomIndexes(originIndexes.length), originIndexes);

    }

    /**
     * 优化四条牌
     * 抽掉最后一张牌
     * @param players 全部玩家数组
     * @param currentPlayerIndex 当前玩家在数组里的索引
     */
    private void optimizeFourOfAKindCards(Player[] players, int currentPlayerIndex){
        Map<String, Integer> cardFaceMap = GameUtils.cardFaceArrayToMap(cards);
        final String[] skipFace = new String[1];
        cardFaceMap.forEach((key, value) -> {
            if (value >= 4) {
                skipFace[0] = key;
            }
        });

        int[] originIndexes = getIndexesFromSkipFaces(skipFace[0]);

        //抽牌
        changeCard(getRandomPlayer(players, currentPlayerIndex), getRandomIndexes(originIndexes.length), originIndexes);
    }

    private Player getRandomPlayer(Player[] players, int currentPlayerIndex){
        Random r = new Random(System.currentTimeMillis());
        int randomIndex = -1;
        while(randomIndex == -1 || randomIndex == currentPlayerIndex){
            randomIndex = r.nextInt(players.length);
        }
        return players[randomIndex];
    }

    private int[] getRandomIndexes(int size){
        Set<Integer> randomSet = new HashSet<>();
        while(randomSet.size() < size){
            randomSet.add(new Random(System.currentTimeMillis()).nextInt(5));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return randomSet.stream().mapToInt(Integer::intValue).toArray();

    }

    private int[] getIndexesFromSkipFaces(String skipFace){
        List<Integer> originIndexes = new ArrayList<>();

        for (int i = 0; i < cards.length; i++) {
            if(!cards[i].getFace().getFace().equals(skipFace)){
                originIndexes.add(i);
            }
        }
        return originIndexes.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public void changeCard(Player targetPlayer, int[] targetCardIndexes, int[] originCardIndexes) {
        if(targetPlayer instanceof RobotPlayer){
            System.out.println("玩家" + index + "从玩家" + ((RobotPlayer) targetPlayer).getIndex() + "抽走了" + targetCardIndexes.length + "张牌");
        }
        super.changeCard(targetPlayer, targetCardIndexes, originCardIndexes);
    }
}
