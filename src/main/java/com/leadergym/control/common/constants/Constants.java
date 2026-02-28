package com.leadergym.control.common.constants;

import java.time.ZoneId;

public class Constants {
    public static final String MEMBER_NOT_FOUND = "Member not found with ID: ";
    public static final String PLAN_NOT_FOUND = "Plan not found with ID: ";
    public static final String CHECKIN_NOT_FOUND = "Check-in not found with ID: ";
    public static final String RECEIPT_NOT_FOUND = "Receipt not found with ID: ";

    //Plan types
    public static final String PLAN_TYPE_MONTHLY_FREE = "MONTHLY_FREE";
    public static final String PLAN_TYPE_MONTHLY_THREE_DAYS = "THREE_TIMES_PER_WEEK";
    public static final String PLAN_TYPE_FIVETEEN_DAYS = "FIFTEEN_DAYS";
    public static final String PLAN_TYPE_WEEKLY = "WEEKLY";
    public static final String PLAN_TYPE_DAILY = "DAILY";

    //DAYS DURATION
    public static final int DURATION_MONTHLY = 30;
    public static final int DURATION_WEEKLY = 7;
    public static final int DURATION_DAILY = 1;

    public static final ZoneId ARGENTINA_TIME_ZONE = ZoneId.of("America/Argentina/Buenos_Aires");
}
