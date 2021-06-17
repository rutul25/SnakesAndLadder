package utils;

import java.util.Random;

public class DiceUtils {

    public static int roll() {
        return new Random().nextInt(6) + 1;
    }
}
