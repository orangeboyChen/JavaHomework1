package com.nowcent.service.impl;

import com.nowcent.pojo.Card;
import com.nowcent.player.Player;
import com.nowcent.player.RobotPlayer;
import com.nowcent.service.DeckOfCards;
import com.nowcent.service.GameService;

import java.util.*;

/**
 * @author orangeboy
 * @version 1.0
 * @date 2020/10/20 23:35
 */
public class GameServiceImpl implements GameService {
    private final Player[] players;
    private final DeckOfCards deckOfCards;
    private final Player currentPlayer;
    private int currentPlayerIndex = -1;

    private GameServiceImpl(GameServiceBuilder gameServiceBuilder){
        players = new Player[gameServiceBuilder.robotPlayerTotal + 1];
        deckOfCards = gameServiceBuilder.deckOfCards;
        currentPlayer = gameServiceBuilder.currentPlayer;
    }


    /**
     * 构造者模式
     */
    public static class GameServiceBuilder{
        private final int BIGGEST_ROBOT_PLAYER_COUNT = 5;
        private int robotPlayerTotal = 2;
        private final DeckOfCards deckOfCards;
        private final Player currentPlayer;


        public GameServiceBuilder robotPlayerTotal(int robotPlayerTotal){
            //非法输入检测
            if(robotPlayerTotal > BIGGEST_ROBOT_PLAYER_COUNT){
                throw new RuntimeException("电脑玩家个数不能大于5");
            }

            //设值
            this.robotPlayerTotal = robotPlayerTotal;
            return this;
        }

        public GameServiceBuilder(DeckOfCards deckOfCards, Player currentPlayer){
            this.deckOfCards = deckOfCards;
            this.currentPlayer = currentPlayer;
        }

        public GameService build(){
            return new GameServiceImpl(this);
        }

    }



    @Override
    public void initGame(){
        //洗牌
        deckOfCards.shuffle();

        //决定次序
        Random r = new Random(System.currentTimeMillis());
        currentPlayerIndex = r.nextInt(players.length);

        //设定玩家座位
        players[currentPlayerIndex] = currentPlayer;

        //发牌
        for (int i = 0; i < players.length; i++) {
            //添加机器人
            if(players[i] == null){
                players[i] = new RobotPlayer();
                players[i].setCards(new Card[]{deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard()});
            }
            //真实玩家发牌，并提示
            else{
                players[i].setCards(new Card[]{deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard()});
                System.out.printf("你的牌是\n%s（%s）\n", Arrays.toString(currentPlayer.getCards()), currentPlayer.getCardScore());

            }
        }

        //准备结束，开始玩游戏
        playOneGame();

    }

    /**
     * 玩一局
     */
    public void playOneGame(){
        System.out.println("\n===游戏开始===");

        //开始抽牌
        for (int i = 0; i < players.length; i++) {
            if(i != currentPlayerIndex){
                System.out.printf("玩家%d正在抽牌\n", i + 1);
            }
            else{
                System.out.println("【到你的回合了】");
            }

            players[i].optimizeCards(deckOfCards);
        }

        //统计最大分数，展示每个玩家抽的牌
        int maxScore = currentPlayer.getCardScore().getIndex();
        System.out.println("===");
        System.out.printf("你抽到了%s\n", currentPlayer.getCardScore());
        for (int i = 0; i < players.length; i++) {
            if(i == currentPlayerIndex) {
                continue;
            }
            System.out.printf("玩家%d抽到了%s（%s）\n", (i + 1), Arrays.toString(players[i].getCards()), players[i].getCardScore());
            if(players[i].getCardScore().getIndex() >= maxScore){
                maxScore = players[i].getCardScore().getIndex();
            }
        }
        System.out.println("===");

        //展示输赢结果
        if(currentPlayer.getCardScore().getIndex() >= maxScore){
            System.out.println("你赢得了游戏");
        }
        else{
            System.out.println("你输了");
        }
    }

}
