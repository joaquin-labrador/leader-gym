package com.leadergym.control.common.enums;

public enum PlanType {
    MONTHLY_FREE(30),
    THREE_TIMES_PER_WEEK(30),
    FIFTEEN_DAYS(15),
    WEEKLY(7),
    DAILY(1);

    PlanType(int durationInDays) {
    }

    public int getDurationInDays() {
        return switch (this) {
            case MONTHLY_FREE, THREE_TIMES_PER_WEEK -> 30;
            case FIFTEEN_DAYS -> 15;
            case WEEKLY -> 7;
            case DAILY -> 1;
            default -> throw new IllegalArgumentException("Unknown plan type: " + this);
        };

    }
}
