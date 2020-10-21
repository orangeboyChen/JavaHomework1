package com.nowcent.service.impl;

import com.nowcent.pojo.Card;
import com.nowcent.pojo.Player;
import com.nowcent.pojo.RobotPlayer;
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

    private GameServiceImpl(GameServiceBuilder gameServiceBuilder){
        players = new Player[gameServiceBuilder.robotPlayerTotal + 1];
        deckOfCards = gameServiceBuilder.deckOfCards;
        currentPlayer = gameServiceBuilder.currentPlayer;
    }


    public static class GameServiceBuilder{
        private int robotPlayerTotal = 2;
        private final DeckOfCards deckOfCards;
        private final Player currentPlayer;


        public GameServiceBuilder playerTotal(int playerTotal){
            if(playerTotal > 5){
                throw new RuntimeException("电脑玩家个数不能大于5");
            }
            this.robotPlayerTotal = playerTotal;
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
        deckOfCards.shuffle();
        players[0] = currentPlayer;
        players[0].setCards(new Card[]{deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard()});
        System.out.printf("你的牌是\n%s（%s）\n", Arrays.toString(players[0].getCards()), players[0].getCardScore());

        for (int i = 1; i < players.length; i++) {
            players[i] = new RobotPlayer();
            players[i].setCards(new Card[]{deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard(), deckOfCards.dealCard()});
        }
        playOneGame();

    }

    public void playOneGame(){
        players[0].optimizeCards(deckOfCards);
        System.out.printf("替换后，你的牌是%s（%s）\n", Arrays.toString(players[0].getCards()), players[0].getCardScore());
        System.out.println("===");

        for (int i = 1; i < players.length; i++) {
            System.out.printf("玩家%d正在抽牌\n", i + 1);
            players[i].optimizeCards(deckOfCards);
        }

        int maxScore = players[0].getCardScore().getIndex();
        System.out.println("===");
        System.out.printf("你抽到了%s\n", players[0].getCardScore());
        for (int i = 1; i < players.length; i++) {
            System.out.printf("玩家%d抽到了%s（%s）\n", (i + 1), Arrays.toString(players[i].getCards()), players[i].getCardScore());
            if(players[i].getCardScore().getIndex() >= maxScore){
                maxScore = players[i].getCardScore().getIndex();
            }
        }

        System.out.println("===");
        if(currentPlayer.getCardScore().getIndex() >= maxScore){
            System.out.println("你赢得了游戏");
        }
        else{
            System.out.println("你输了");
        }
    }

}
