package org.xuanzhaopeng.common.common;

import java.util.HashMap;
import java.util.Map;

public class TimeDelta implements Comparable<TimeDelta> {
    private static final int MILLISECONDS_IN_SECOND = 1000;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;

    private static final long CACHE_RANGE =
            HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLISECONDS_IN_SECOND;

    private long milliSeconds;

    private static final Map<Long, TimeDelta> cache = new HashMap<>();

    private TimeDelta(double milliSeconds) {
        this.milliSeconds = (long) milliSeconds;
    }

    private static TimeDelta getInstance(double milliSeconds) {
        if (-CACHE_RANGE <= milliSeconds && milliSeconds <= CACHE_RANGE) {
            final long milliSecondsLong = (long) milliSeconds;
            if (!cache.containsKey(milliSecondsLong)) {
                cache.put(milliSecondsLong, new TimeDelta(milliSeconds));
            }
            return cache.get(milliSecondsLong);
        }
        return new TimeDelta(milliSeconds);
    }

    public static TimeDelta fromSeconds(double seconds) {
        return getInstance(seconds * MILLISECONDS_IN_SECOND);
    }

    public int asSeconds() {
        return (int) (this.milliSeconds / MILLISECONDS_IN_SECOND);
    }

    public long asMilliSeconds() {
        return this.milliSeconds;
    }

    public int asMinutes() {
        return (int) this.asFloatMinutes();
    }

    public double asFloatMinutes() {
        return this.milliSeconds * 1.0 / SECONDS_IN_MINUTE / MILLISECONDS_IN_SECOND;
    }

    public int asHours() {
        return (int) this.asFloatHours();
    }

    public double asFloatHours() {
        return this.milliSeconds * 1.0 / MINUTES_IN_HOUR / SECONDS_IN_MINUTE / MILLISECONDS_IN_SECOND;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof TimeDelta) &&
                (Math.abs(((TimeDelta) other).asMilliSeconds()) == Math.abs(this.asMilliSeconds()));
    }

    @Override
    public String toString() {
        final int hours = this.asHours();
        final int minutes = this.asMinutes() % MINUTES_IN_HOUR;
        final int seconds = this.asSeconds() % SECONDS_IN_MINUTE;
        final long milliSeconds = this.asMilliSeconds() % MILLISECONDS_IN_SECOND;
        if (hours != 0) {
            return String.format((this.milliSeconds < 0 ? "- " : "") + "%02d h:%02d m:%02d s::%d ms",
                    Math.abs(hours), Math.abs(minutes), Math.abs(seconds), Math.abs(milliSeconds));
        } else if (minutes != 0) {
            return String.format((this.milliSeconds < 0 ? "- " : "") + "%02d m:%02d s::%d ms",
                    Math.abs(minutes), Math.abs(seconds), Math.abs(milliSeconds));
        } else if (seconds != 0) {
            return String.format((this.milliSeconds < 0 ? "- " : "") + "%02d s::%d ms",
                    Math.abs(seconds), Math.abs(milliSeconds));
        }
        return String.format("%d ms", milliSeconds);
    }

    @Override
    public int compareTo(TimeDelta o) {
        if (o == null) {
            throw new NullPointerException("null value is not comparable");
        }
        if (this.equals(o)) {
            return 0;
        }
        if (Math.abs(this.milliSeconds) > Math.abs(o.milliSeconds)) {
            return 1;
        }
        return -1;
    }
}
