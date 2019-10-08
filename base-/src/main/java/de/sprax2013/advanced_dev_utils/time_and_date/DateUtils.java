package de.sprax2013.advanced_dev_utils.time_and_date;

import java.time.LocalDate;

public class DateUtils {
    /**
     * Returns a <i>LocalDate</i>-Object of Easter Sunday for a specific year
     *
     * @throws IllegalArgumentException When <b>year</b> is negative
     */
    public static LocalDate getEasterSunday(int year) {
        if (year < 0) {
            throw new IllegalArgumentException("There is no date for Easter B.C.");
        }

        int a = year % 19;
        int b = year / 100;

        int h = (19 * a + b - (b / 4) - ((8 * b + 13) / 25) + 15) % 30;

        int c = year % 100;
        int m = (a + 11 * h) / 319;
        int r = (2 * (b % 4) + 2 * (c / 4) - (c % 4) - h + m + 32) % 7;

        int easterMonth = (h - m + r + 90) / 25;
        int easterDay = (h - m + r + easterMonth + 19) % 32;

        return LocalDate.of(year, easterMonth, easterDay);
    }
}