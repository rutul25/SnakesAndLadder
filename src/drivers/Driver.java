package drivers;

import models.Ladder;
import models.Player;
import models.Snake;
import services.SnakeAndLadderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int noOfSnakes = scanner.nextInt();
        List<Snake> snakes = new ArrayList<>();

        for(int index = 0; index < noOfSnakes; index++) {
            int head = scanner.nextInt();
            int tail = scanner.nextInt();
            Snake snake = new Snake(head, tail);
            snakes.add(snake);
        }

        int noOfLadders = scanner.nextInt();
        List<Ladder> ladders = new ArrayList<>();

        for(int index = 0; index < noOfLadders; index++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            Ladder ladder = new Ladder(start, start);
            ladders.add(ladder);
        }

        int noOfPlayers = scanner.nextInt();
        List<Player> players = new ArrayList<>();
        for(int index = 0; index < noOfPlayers; index++) {
            players.add(new Player(scanner.next()));
        }

        SnakeAndLadderService snakeAndLadderService = new SnakeAndLadderService();
        snakeAndLadderService.setPlayers(players);
        snakeAndLadderService.setLadders(ladders);
        snakeAndLadderService.setSnakes(snakes);

        snakeAndLadderService.startGame();

    }
}
