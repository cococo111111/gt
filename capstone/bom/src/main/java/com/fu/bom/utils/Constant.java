package com.fu.bom.utils;

/**
 * Created by PhucNT on 9/23/2016.
 */
public class Constant {
    public static final String SUCCESS = "SUCCESS";

    public static final String ENTRIES_PRODUCT = "Product";

    public static final int KEY_ORIGINAL=0;

    public static final String PROMOTION_EXTENSION="_km.png";

    public static final long ONEDAY=86399000;

    public enum ACCOUNT_ROLE_ID {
        ADMIN(1), STAFF(2);

        private int value;

        public int getValue() {
            return value;
        }

        private ACCOUNT_ROLE_ID(int value) {
            this.value = value;
        }
    }

    public enum ACCOUNT_STATUS_ID {
        ACTIVE(1), DEACTIVATE(2);

        private int value;

        public int getValue() {
            return value;
        }

        private ACCOUNT_STATUS_ID(int value) {
            this.value = value;
        }
    }
}
