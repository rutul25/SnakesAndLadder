package services;

import models.Ladder;
import models.Player;
import models.Snake;
import models.SnakeAndLadderBoard;
import utils.DiceUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class SnakeAndLadderService {

    private SnakeAndLadderBoard snakeAndLadderBoard;
    private int initialNumberOfPlayers;
    private Queue<Player> players;

    private int noOfDices;

    private static final int DEFAULT_BOARD_SIZE = 100;
    private static final int DEFAULT_NO_OF_DICES = 1;

    public SnakeAndLadderService(int boardSize) {
        this.snakeAndLadderBoard = new SnakeAndLadderBoard(boardSize);
        this.players = new LinkedList<>();
        this.noOfDices = SnakeAndLadderService.DEFAULT_NO_OF_DICES;
    }

    public SnakeAndLadderService() {
        this(SnakeAndLadderService.DEFAULT_BOARD_SIZE);
    }

    /**
     * =====================Initialising the board==================
     */

    public void setPlayers(List<Player> players) {
        this.initialNumberOfPlayers = players.size();
        Map<String, Integer> playerPieces = new HashMap<>();
        for(Player player : players) {
            playerPieces.put(player.getId(), 0);
            this.players.add(player);
        }
        this.snakeAndLadderBoard.setPlayerPieces(playerPieces);
    }

    public void setSnakes(List<Snake> snakes) {
        this.snakeAndLadderBoard.setSnakes(snakes);
    }

    public void setLadders(List<Ladder> ladders) {
        this.snakeAndLadderBoard.setLadders(ladders);
    }


    /**
     * ===============Game Logic=================
     */


    private int getNewPositionAfterGoingThroughSnakesAndLadder(int currentPosition) {
        int previousPosition;

        do {
            previousPosition = currentPosition;
            for(Snake snake : snakeAndLadderBoard.getSnakes()) {
                if(snake.getHead() == currentPosition) {
                    currentPosition = snake.getTail();
                }
            }

            for(Ladder ladder : snakeAndLadderBoard.getLadders()) {
                if(ladder.getStart() == currentPosition) {
                    currentPosition = ladder.getEnd();
                }
            }
        } while (currentPosition != previousPosition);
        return currentPosition;
    }

    private void movePlayer(Player currentPlayer, int positions) {
        int oldPosition = snakeAndLadderBoard.getPlayerPieces().get(currentPlayer.getId());

        int newPosition = oldPosition + positions;

        int boardSize = snakeAndLadderBoard.getSize();

        if(newPosition > boardSize) {
            newPosition = oldPosition;
        } else {
            newPosition = getNewPositionAfterGoingThroughSnakesAndLadder(newPosition);
        }

        snakeAndLadderBoard.getPlayerPieces().put(currentPlayer.getId(), newPosition);

        System.out.println(currentPlayer.getName() + " rolled a " + positions +" and moved from " + oldPosition + " to " + newPosition);
    }

    private boolean isGameCompleted() {
        int currentNumberOfPlayers = players.size();
        return currentNumberOfPlayers < initialNumberOfPlayers;
    }

    private int getTotalDiceValueAfterRoll() {
        /**
         * Here, logic can be extended to roll dice multiple time if number of Dice is > 1
         */
        return DiceUtils.roll();
    }

    public boolean hasPlayerWon(Player player) {
        int playerPosition = snakeAndLadderBoard.getPlayerPieces().get(player.getId());
        int winningPosition = snakeAndLadderBoard.getSize();

        return playerPosition == winningPosition;
    }

    public void startGame() {
        while(!isGameCompleted()) {
            int totalDiceValue = getTotalDiceValueAfterRoll();
            Player currentPlayer = players.poll();
            movePlayer(currentPlayer, totalDiceValue);
            if (hasPlayerWon(currentPlayer)) {
                System.out.println(currentPlayer.getName() +" wins the game");
                snakeAndLadderBoard.getPlayerPieces().remove(currentPlayer.getId());
            } else {
                players.add(currentPlayer);
            }
        }
    }
}
