package com.group2.bookstoreproject.base.common;
import com.group2.bookstoreproject.R;

import java.util.Arrays;

public class Constants {
    public final static String GLOBAL_NOTIFICATION_ID = "GlobalID";
    public final static int ADMIN = 1;
    public final static int CUSTOMER = 2;
    public final static int SHIPPER = 3;

    public final static int CREATE_MODE = 10;
    public final static int UPDATE_MODE = 11;
    public final static String MODE_KEY = "mode";

    public static class BottomNav {

        //customer
        public static final int[] CUS_HOME = {
                R.id.navigation_cus_home
        };

        public static final int[] CUS_CATEGORY = {
                R.id.navigation_category, R.id.bookDetailsFragment2
        };

        public static final int[] CUS_CHAT = {
                R.id.navigation_chat
        };

        public static final int[] CUS_PROFILE = {
                R.id.navigation_cus_profile,R.id.update_profile,R.id.orderListFragment,R.id.orderDetailsFragment
        };

        public static final int[][] CUS_NAV = {
                CUS_HOME,CUS_CATEGORY,CUS_CHAT,CUS_PROFILE
        };

        // admin
        public static final  int[] ADMIN_BOOKS = {
                R.id.navigation_books,R.id.bookDetailsFragment, R.id.upsertBookFragment
        };
        public static final  int[] ADMIN_CHAT = {
                R.id.navigation_chatlist, R.id.chat_fragment
        };
        public static final  int[] ADMIN_PROFILE = {
                R.id.navigation_admin_profile
        };

        public static final  int[] ADMIN_ORDER = {
                R.id.navigation_order,R.id.orderDetailsFragment
        };

        public static final int[][] ADMIN_NAV = {
                ADMIN_BOOKS,ADMIN_CHAT,ADMIN_PROFILE,ADMIN_ORDER
        };
        //shipper
        public static final  int[] SHIPPER_ORDER = {
                R.id.navigation_order_list, R.id.orderDetailsFragment
        };

        public static final int[] SHIPPER_PROFILE = {
                R.id.navigation_shipper_profile
        };

        public static final int[][] SHIPPER_NAV = {
                SHIPPER_ORDER,SHIPPER_PROFILE
        };

        public static boolean isInNav(int id, int[] nav){
            return Arrays.stream(nav).anyMatch(i -> i==id);
        }

        public static int getNavActive(int id, int[][] nav){
           for(int[] navigation : nav){
               if(isInNav(id, navigation)) return navigation[0];
           }
           return nav[0][0];
        }

    }

}
