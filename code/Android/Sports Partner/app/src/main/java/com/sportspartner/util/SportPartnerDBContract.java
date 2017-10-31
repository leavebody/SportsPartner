package com.sportspartner.util;

import android.provider.BaseColumns;

/**
 * Created by xc on 10/23/17.
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
    }
}
