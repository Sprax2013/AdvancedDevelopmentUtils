/*
 * Created by Sprax2013 (developer@sprax2013.de)
 */
package de.sprax2013.advanced_dev_utils.time_and_date;

public class Time {
    // Variables
    private Long seconds;
    private Long minutes;
    private Long hours;

    // Constructors

    /**
     * Same as <i>new Time(0, 0, 0);</i>
     *
     * @see #Time(long, long, long)
     * @see #Time(long)
     */
    public Time() {
        seconds = 0L;
        minutes = 0L;
        hours = 0L;
    }

    /**
     * Use this to create an Object that will calculate given variables.<br>
     * Example: new Time(0, 62, 0); -> 1 Hour & 2 Minutes ({@link #getHours()},
     * {@link #getMinutes()})<br>
     * You can use {@link #getUncalculatedMinutes()} to get 62 minutes.
     *
     * @throws IllegalArgumentException When <b>seconds</b>, <b>minutes</b> or
     *                                  <b>hours</b> is negative
     * @see #Time()
     * @see #Time(long, long, long)
     */
    public Time(long seconds, long minutes, long hours) {
        if (seconds < 0) {
            throw new IllegalArgumentException("'seconds' can not be negative");
        } else if (minutes < 0) {
            throw new IllegalArgumentException("'minutes' can not be negative");
        } else if (hours < 0) {
            throw new IllegalArgumentException("'hours' can not be negative");
        }

        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;

        recalculate();
    }

    /**
     * Same as {@code new Time(milliseconds / 1_000, 0, 0);}<br>
     *
     * @throws IllegalArgumentException When <b>milliseconds</b> is negative
     * @see #Time()
     * @see #Time(long, long, long)
     */
    public Time(long milliseconds) {
        if (milliseconds < 0) {
            throw new IllegalArgumentException("'milliseconds' can not be negative");
        }

        seconds = milliseconds / 1_000;
        minutes = 0L;
        hours = 0L;

        recalculate();
    }

    // Get calculated values

    /**
     * Returns the seconds (calculated)<br>
     * <b>Examples:</b>
     * <ul>
     * <li>new Time(25, 12, 1); -> 25 Seconds [+12 Minutes & +1 Hour]</li>
     * <li>new Time(70, 10, 0); -> 10 Seconds [+11 Minutes]</li>
     * <li>new Time(0, 60, 1); -> 0 Seconds [+2 Hours]</li>
     * </ul>
     *
     * @return Seconds (calculated)
     *
     * @see #getMinutes()
     * @see #getHours()
     */
    public long getSeconds() {
        return this.seconds;
    }

    /**
     * Returns the minutes (calculated)<br>
     * <b>Examples:</b>
     * <ul>
     * <li>new Time(25, 12, 1); -> 12 Minutes [+25 Seconds & +1 Hour]</li>
     * <li>new Time(70, 10, 0); -> 11 Minutes [+10 Seconds]</li>
     * <li>new Time(0, 60, 1); -> 0 Minutes [+2 Hours]</li>
     * </ul>
     *
     * @return Minutes (calculated)
     *
     * @see #getSeconds()
     * @see #getHours()
     */
    public long getMinutes() {
        return this.minutes;
    }

    /**
     * Returns the hours (calculated)<br>
     * <b>Examples:</b>
     * <ul>
     * <li>new Time(25, 12, 1); -> 1 Hour [+25 Seconds & +12 Minutes]</li>
     * <li>new Time(70, 10, 0); -> 0 Hours [+10 Seconds & +11 Minutes]</li>
     * <li>new Time(10, 60, 1); -> 2 Hours [+10 Seconds]</li>
     * </ul>
     *
     * @return Hours (calculated)
     *
     * @see #getSeconds()
     * @see #getMinutes()
     */
    public long getHours() {
        return this.hours;
    }

    // Get non-calculated values

    /**
     * Returns the seconds but uncalculated.<br>
     * <b>Examples:</b>
     * <ul>
     * <li>new Time(25, 20, 1); -> 4825 Seconds [(1h * 60 + 20min) * 60 +
     * 25sec]</li>
     * <li>new Time (10, 20, 0); -> 1210 Seconds [(0h * 60 + 20min) * 60 +
     * 10sec]</li>
     * <li>new Time(0, 20, 0) -> 1200 Seconds [(0h * 60 + 20min) * 60 + 0sec]</li>
     * </ul>
     *
     * @return seconds (uncalculated)
     *
     * @see #getUncalculatedMinutes()
     * @see #getUncalculatedHours()
     */
    public long getUncalculatedSeconds() {
        return (getHours() * 60 + getMinutes()) * 60 + seconds;
    }

    /**
     * Returns the minutes but uncalculated.<br>
     * <b>Examples:</b>
     * <ul>
     * <li>new Time(0, 20, 1); -> 80 Minutes [1h * 60 + 20min]</li>
     * <li>new Time (10, 20, 0); -> 20 Minutes [0h * 60 + 20min]</li>
     * <li>new Time(0, 20, 0) -> 20 Minutes [0h * 60 + 20min]</li>
     * </ul>
     *
     * @return minutes (uncalculated)
     *
     * @see #getUncalculatedSeconds()
     * @see #getUncalculatedHours()
     */
    public long getUncalculatedMinutes() {
        return getHours() * 60 + getMinutes();
    }

    /**
     * Use {@link #getHours()} instead.
     *
     * @return value of {@link #getHours()}
     */
    public long getUncalculatedHours() {
        return getHours();
    }

    // Get values (as String) with a leading zero

    /**
     * Gets the seconds with a leading zero.<br>
     * <b>Examples:</b>
     * <ul>
     * <li>6 Seconds -> "06"</li>
     * <li>14 Seconds -> "14"</li>
     * </ul>
     *
     * @return the seconds with a leading zero or just the seconds if it has at
     * least two digits
     */
    public String getSecondsWithLeadingZero() {
        String seconds = this.seconds.toString();

        if (seconds.length() < 2) {
            return "0" + seconds;
        }

        return seconds;
    }

    /**
     * Gets the minutes with a leading zero.<br>
     * <b>Examples:</b>
     * <ul>
     * <li>6 Minutes -> "06"</li>
     * <li>14 Minutes -> "14"</li>
     * </ul>
     *
     * @return the minutes with a leading zero or just the minutes if it has at
     * least two digits
     */
    public String getMinutesWithLeadingZero() {
        String minutes = this.minutes.toString();

        if (minutes.length() < 2) {
            return "0" + minutes;
        }

        return minutes;
    }

    /**
     * Gets the hours with a leading zero.<br>
     * <b>Examples:</b>
     * <ul>
     * <li>6 Hours -> "06"</li>
     * <li>14 Hours -> "14"</li>
     * </ul>
     *
     * @return the hours with a leading zero or just the hours if it has at least
     * two digits
     */
    public String getHoursWithLeadingZero() {
        String hours = this.hours.toString();

        if (hours.length() < 2) {
            return "0" + hours;
        }

        return hours;
    }

    // Add-Methods

    /**
     * Adds <b>seconds</b> and calls {@link #recalculate()}
     *
     * @throws IllegalArgumentException When <b>seconds</b> is negative
     */
    public void addSeconds(long seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("'seconds' can not be negative");
        }

        this.seconds += seconds;

        recalculate();
    }

    /**
     * Adds <b>minutes</b> and calls {@link #recalculate()}
     *
     * @throws IllegalArgumentException When <b>minutes</b> is negative
     */
    public void addMinutes(long minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("'minutes' can not be negative");
        }

        this.minutes += minutes;

        recalculate();
    }

    /**
     * Adds <b>hours</b> and calls {@link #recalculate()}
     *
     * @throws IllegalArgumentException When <b>hours</b> is negative
     */
    public void addHours(long hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("'hours' can not be negative");
        }

        this.hours += hours;
    }

    /**
     * Adds <b>seconds</b>, <b>minutes</b> and <b>hours</b> and calls
     * {@link #recalculate()}.
     *
     * @throws IllegalArgumentException When <b>seconds</b>, <b>minutes</b> or
     *                                  <b>hours</b> is negative
     */
    public void add(long seconds, long minutes, long hours) {
        if (seconds < 0) {
            throw new IllegalArgumentException("'seconds' can not be negative");
        } else if (minutes < 0) {
            throw new IllegalArgumentException("'minutes' can not be negative");
        } else if (hours < 0) {
            throw new IllegalArgumentException("'hours' can not be negative");
        }

        this.seconds += seconds;
        this.minutes += minutes;
        this.hours += hours;

        recalculate();
    }

    // Add-Methods by Time

    /**
     * Adds the calculated seconds from <b>time</b> and calls {@link #recalculate()}
     */
    public void addSeconds(Time time) {
        seconds += time.getSeconds();

        recalculate();
    }

    /**
     * Adds the calculated minutes from <b>time</b> and calls {@link #recalculate()}
     */
    public void addMinutes(Time time) {
        minutes += time.getMinutes();

        recalculate();
    }

    /**
     * Adds the calculated hours from <b>time</b>
     */
    public void addHours(Time time) {
        hours += time.getHours();
    }

    /**
     * Adds the calculated <b>seconds</b>, <b>minutes</b> and <b>hours</b> from
     * <b>time</b> and calls {@link #recalculate()}.
     */
    public void add(Time time) {
        seconds += time.getSeconds();
        minutes += time.getMinutes();
        hours += time.getHours();

        recalculate();
    }

    // Subtract-Methods

    /**
     * Subtracts <b>seconds</b> and calls {@link #recalculate()}
     *
     * @throws IllegalArgumentException When <b>seconds</b> is negative
     */
    public void subtractSeconds(long seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("'seconds' can not be negative");
        }

        this.seconds -= seconds;

        recalculate();
    }

    /**
     * Subtracts <b>minutes</b> and calls {@link #recalculate()}
     *
     * @throws IllegalArgumentException When <b>minutes</b> is negative
     */
    public void subtractMinutes(long minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("'minutes' can not be negative");
        }

        this.minutes -= minutes;

        recalculate();
    }

    /**
     * Subtracts <b>hours</b> and calls {@link #recalculate()}
     *
     * @throws IllegalArgumentException When <b>hours</b> is negative
     */
    public void subtractHours(long hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("'hours' can not be negative");
        }

        this.hours -= hours;

        recalculate();
    }

    /**
     * Subtracts <b>seconds</b>, <b>minutes</b> and <b>hours</b> and calls
     * {@link #recalculate()}.
     *
     * @throws IllegalArgumentException When <b>seconds</b>, <b>minutes</b> or
     *                                  <b>hours</b> is negative
     */
    public void subtract(long seconds, long minutes, long hours) {
        if (seconds < 0) {
            throw new IllegalArgumentException("'seconds' can not be negative");
        } else if (minutes < 0) {
            throw new IllegalArgumentException("'minutes' can not be negative");
        } else if (hours < 0) {
            throw new IllegalArgumentException("'hours' can not be negative");
        }

        this.seconds -= seconds;
        this.minutes -= minutes;
        this.hours -= hours;

        recalculate();
    }

    // Subtract-Methods by Time

    /**
     * Subtracts the calculated seconds from <b>time</b> and calls
     * {@link #recalculate()}
     */
    public void subtractSeconds(Time time) {
        seconds -= time.getSeconds();

        recalculate();
    }

    /**
     * Subtracts the calculated minutes from <b>time</b>and calls
     * {@link #recalculate()}
     */
    public void subtractMinutes(Time time) {
        minutes -= time.getMinutes();

        recalculate();
    }

    /**
     * Subtracts the calculated hours from <b>time</b> and calls
     * {@link #recalculate()}
     */
    public void subtractHours(Time time) {
        hours -= time.getHours();

        recalculate();
    }

    /**
     * Subtracts the calculated <b>seconds</b>, <b>minutes</b> and <b>hours</b> from
     * <b>time</b> and calls {@link #recalculate()}.
     */
    public void subtract(Time time) {
        seconds -= time.getSeconds();
        minutes -= time.getMinutes();
        hours -= time.getHours();

        recalculate();
    }

    // Private-Methods

    /**
     * Recalculates the variables used in {@link #getSeconds()},
     * {@link #getMinutes()} and {@link #getHours()}.<br>
     * <b>This method makes sure that these variables never fall below 0!</b><br>
     * This method will be called when one of them is modified.
     */
    private void recalculate() {
        if (seconds < 0) {
            seconds = 0L;
        }

        if (minutes < 0) {
            minutes = 0L;
        }

        if (hours < 0) {
            hours = 0L;
        }

        while (seconds >= 60) {
            seconds = seconds - 60;
            minutes++;
        }

        while (minutes >= 60) {
            minutes = minutes - 60;
            hours++;
        }
    }
}