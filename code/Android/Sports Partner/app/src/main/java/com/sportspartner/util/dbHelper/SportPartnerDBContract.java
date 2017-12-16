package com.sportspartner.util.dbHelper;

import android.provider.BaseColumns;

/**
 * @author Xiaochen Li
 */

public final class SportPartnerDBContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private SportPartnerDBContract() {}

    /* Inner class that defines the table contents */
    public static class LoginDB implements BaseColumns {
        public static final String TABLE_NAME = "login";
        public static final String COLUMN_EMAIL_NAME = "email";
        public static final String COLUMN_KEY_NAME = "key";
        public static final String COLUMN_REGISTRATIONID_NAME = "registerId";
    }

    /* Inner class that defines the table contents */
    public static class NotificationDB implements BaseColumns {
        public static final String TABLE_NAME = "notification";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE_NAME = "title";
        public static final String COLUMN_DETAIL_NAME = "detail";
        public static final String COLUMN_SENDER_NAME = "sender";
        public static final String COLUMN_TYPE_NAME = "type";
        public static final String COLUMN_TIME_NAME = "time";
        public static final String COLUMN_PRIORITY_NAME = "priority";

    }

    /* Inner class that defines the table contents */
    public static class NightModeDB implements BaseColumns {
        public static final String TABLE_NAME = "nightmode";
        public static final String COLUMN_USERID = "userId";
        public static final String COLUMN_START_TIME = "startTime";
        public static final String COLUMN_END_TIME = "endTime";
    }

    public static class ActivityNotiDB implements BaseColumns{
        public static final String TABLE_NAME = "activitynoti";
        public static final String COLUMN_ACTIVITYID = "activityId";
        public static final String COLUMN_START_TIME = "startTime";
    }
}
