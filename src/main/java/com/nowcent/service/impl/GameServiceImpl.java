package com.nowcent.service.impl;

import com.nowcent.player.RealPlayer;
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
    private final int robotPlayerTotal;
    private Player[] players;
    private final DeckOfCards deckOfCards;
    private final Player currentPlayer;
    private int gameTotal;

    private GameServiceImpl(GameServiceBuilder gameServiceBuilder){
        robotPlayerTotal = gameServiceBuilder.robotPlayerTotal;
        deckOfCards = gameServiceBuilder.deckOfCards;
        currentPlayer = gameServiceBuilder.currentPlayer;
        gameTotal = gameServiceBuilder.gameTotal;

        players = new Player[robotPlayerTotal + 1];
        players[0] = currentPlayer;
        for (int i = 1; i < players.length; i++) {
            players[i] = new RobotPlayer(i + 1);
        }
    }

    private void shufflePosition(){
        for ( int first = 0; first < players.length; first++ ) {
            int second = new Random().nextInt(players.length);
            Player temp = players[first];
            players[first] = players[second];
            players[second] = temp;
        }
    }


    /**
     * 构造者模式
     */
    public static class GameServiceBuilder{
        private final int BIGGEST_ROBOT_PLAYER_COUNT = 5;
        private int robotPlayerTotal = 2;
        private final DeckOfCards deckOfCards;
        private final Player currentPlayer;
        private int gameTotal = 1;


        public GameServiceBuilder robotPlayerTotal(int robotPlayerTotal){
            //非法输入检测
            if(robotPlayerTotal > BIGGEST_ROBOT_PLAYER_COUNT){
                throw new RuntimeException("电脑玩家个数不能大于5");
            }

            //设值
            this.robotPlayerTotal = robotPlayerTotal;
            return this;
        }

        public GameServiceBuilder gameTotal(int gameTotal){
            //非法输入检测
            if(gameTotal <= 0){
                throw new RuntimeException("局数不正确");
            }

            //设值
            this.gameTotal = gameTotal;
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
        //随机化玩家座位
        shufflePosition();

        //洗牌
        deckOfCards.shuffle();

        //发牌
        for (Player player : players) {
            //添加机器人
            if (player instanceof RobotPlayer) {
                player.setCards(new Card[]{deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard()});
            }
            //真实玩家发牌，并提示
            else {
                player.setCards(new Card[]{deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard()});
                System.out.printf("你的牌是\n%s（%s）\n", Arrays.toString(currentPlayer.getCards()), currentPlayer.getCardScore());

            }
        }

    }

    @Override
    public void play(){
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < gameTotal; i++){
            //局数提示
            if(gameTotal >= 2){
                System.out.println("\n\n=============");
                System.out.println("第" + (i + 1) + "/" + gameTotal + "局");
                System.out.println("=============");
            }
            initGame();
            playSingleGame();

            System.out.println("输入回车以继续");
            scanner.nextLine();

        }

        //战绩提示
        if(gameTotal >= 2){
            System.out.println("\n\n=============");
            System.out.println("你赢了" + currentPlayer.getWinCount() + "/" + gameTotal + "局游戏");
            System.out.println("=============");
            System.out.println("玩家       胜局");
            for (Player player : players) {
                if(player instanceof RealPlayer){
                    System.out.print("你           ");
                }
                else{
                    System.out.print("玩家" + ((RobotPlayer)player).getIndex() + "        ");
                }
                System.out.print(player.getWinCount() + "\n");
            }

        }
    }

    /**
     * 玩一局
     */
    public void playSingleGame(){
        System.out.println("\n===游戏开始===");

        //开始抽牌
        for (int i = 0; i < players.length; i++) {
            if(players[i] instanceof RobotPlayer){
                System.out.printf("玩家%d正在抽牌\n", ((RobotPlayer) players[i]).getIndex());
            }
            else{
                System.out.println("\n【到你的回合了】");
            }

            players[i].optimizeCards(deckOfCards);
        }

        //统计最大分数，展示每个玩家抽的牌
        int maxScore = -1;
        System.out.println("===");
        for (int i = 0; i < players.length; i++) {
            if(players[i].getCardScore().getIndex() >= maxScore){
                maxScore = players[i].getCardScore().getIndex();
            }
        }

        //展示牌
        for (int i = 0; i < players.length; i++) {
            if(players[i].getCardScore().getIndex() >= maxScore){
                System.out.print("★ ");
                players[i].winCountIncrement();
            }
            else{
                System.out.print("☆ ");
            }

            if(players[i] instanceof RealPlayer) {
                System.out.printf("你抽到了%s\n", currentPlayer.getCardScore());
            }
            else{
                System.out.printf("玩家%d抽到了%s（%s）\n", ((RobotPlayer)(players[i])).getIndex(), Arrays.toString(players[i].getCards()), players[i].getCardScore());
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
