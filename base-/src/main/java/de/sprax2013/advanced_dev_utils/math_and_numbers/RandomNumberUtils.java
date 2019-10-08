package de.sprax2013.advanced_dev_utils.math_and_numbers;

import java.security.SecureRandom;
import java.util.Random;

public class RandomNumberUtils {
    /**
     * This Method uses a {@link SecureRandom} to return a random number between
     * <b>min</b> and <b>max</b>
     *
     * @param min lowest possible value
     * @param max highest possible value
     *
     * @return The random integer
     *
     * @see #randomInteger(int, int)
     */
    public static Integer secureRandomInteger(int min, int max) {
        if (min == max) {
            return Integer.valueOf(min);
        }

        return new SecureRandom().nextInt(max - min + 1) + min;
    }

    /**
     * This Method uses a {@link Random} to return a random number between
     * <b>min</b> and <b>max</b>
     *
     * @param min lowest possible value
     * @param max highest possible value
     *
     * @return The random integer
     *
     * @see #secureRandomInteger(int, int)
     */
    public static Integer randomInteger(int min, int max) {
        if (min == max) {
            return Integer.valueOf(min);
        }

        return new Random().nextInt(max - min + 1) + min;
    }
}