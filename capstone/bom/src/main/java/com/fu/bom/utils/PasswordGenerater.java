package com.fu.bom.utils;

import java.util.UUID;

/**
 * Created by PhucNT on 9/23/2016.
 */
public class PasswordGenerater {
    public static String random(){
        return UUID.randomUUID().toString().substring(28);
    }
}
