package com.fu.common.util;

import com.fu.common.constant.KeyConstant;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Base64;

/**
 * Created by manlm on 7/25/2016.
 */
public class AESUtil {

    private static final Logger LOG = Logger.getLogger(AESUtil.class);

    private AESUtil() {
    }

    /**
     * encrypt a string
     *
     * @param inputString
     * @param key
     * @return
     */
    public static String encryptByAES(String inputString, String key) {
        LOG.info("[encryptByAES] Start");
        if (inputString != null) {
            String encryptedValue;
            try {
                Key genKey = generateKey(key);
                Cipher c = Cipher.getInstance("AES");
                c.init(Cipher.ENCRYPT_MODE, genKey);
                byte[] encVal = c.doFinal(inputString.getBytes());
                encryptedValue = Base64.getEncoder().encodeToString(encVal);
            } catch (Exception e) {
                LOG.error("[encryptByAES] Exception: " + e);
                return null;
            }
            LOG.info("[encryptByAES] End");
            return encryptedValue;
        } else {
            LOG.info("[encryptByAES] End");
            return null;
        }
    }

    /**
     * decrypt a string
     *
     * @param inputString
     * @param key
     * @return
     */
    public static String decryptByAES(String inputString, String key) {
        LOG.info("[decryptByAES] Start");
        if (inputString != null) {
            String decryptedValue;
            try {
                Key genKey = generateKey(key);
                Cipher c = Cipher.getInstance("AES");
                c.init(Cipher.DECRYPT_MODE, genKey);
                byte[] decodedValue = Base64.getDecoder().decode(inputString);
                byte[] decValue = c.doFinal(decodedValue);
                decryptedValue = new String(decValue, "UTF-8");

            } catch (Exception e) {
                LOG.info("[decryptByAES] Exception: " + e);
                return null;
            }
            LOG.info("[decryptByAES] End");
            return decryptedValue;
        } else {
            LOG.info("[decryptByAES] End");
            return null;
        }
    }

    /**
     * generate key
     *
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Key generateKey(String key) throws UnsupportedEncodingException {
        return new SecretKeySpec(key.getBytes("UTF-8"), "AES");
    }

    public static void main(String[] args) {
       String key="[M@*lm" + KeyConstant.AES_KEY;
//        String des_jdbc=decryptByAES("LjvYjf1LvMOJmdwDEtR7wlEetM5xBY9XeVkcXJoJCNh3u1qXPpyvoEmUECjszWQyYUGi37OUrQ8U/oZS6YsnjzMJBLvihEujvtuZbUxJM7U1FiEhKWvJDXySDdNzgCxc",
//                key);
//        String des2=decryptByAES("gqoC7gWmFKmh4pGsCWnoJolwYjGE02sQ64iZRwMukREPr9ANuYWyJZmkTwJiKj7KOV/YiMjiQIfQx9FiWHn8iQ==",
//                key);
//        String des3=decryptByAES("SzSHg57jXFxycODYVFTHmQ==",
//                key);
//        String des_api_ai=decryptByAES("y3rleVpbIb//dLyngIQTf/BUXXpYrlI0jYefZjExzdSuBnecALSMOVF27WIp7/yq",
//                key);
//        System.out.println(des_jdbc);
//        System.out.println(des2);
//        System.out.println(des3);
//        System.out.println(des_api_ai);
//        String encryptByAES_URL=encryptByAES("jdbc:mysql://localhost:3306/mydb",key);
//        String encryptByAES_USER=encryptByAES("root",key);
//        String encryptByAES_PASSWORD=encryptByAES("1234",key);
//        String encryptByAES_API_AI=encryptByAES("bee3da3662df48bca4d658d0ab639d66",key);
//        System.out.println("***************************");
//        System.out.println("url: "+encryptByAES_URL);
//        System.out.println("user: "+encryptByAES_USER);
//        System.out.println("password: "+encryptByAES_PASSWORD);
//        String desRedisHost=decryptByAES("gqoC7gWmFKmh4pGsCWnoJolwYjGE02sQ64iZRwMukREPr9ANuYWyJZmkTwJiKj7KOV/YiMjiQIfQx9FiWHn8iQ==",key);
//        System.out.println("desRedisHost: "+desRedisHost);
//        System.out.println(" null "+encryptByAES("1234",key));
//        String passRedis=decryptByAES("SzSHg57jXFxycODYVFTHmQ==",key);
//        System.out.println("pass redis host: "+passRedis);

//        String encryptByAES_URL=encryptByAES("jdbc:mysql://gt.co6dkdkuzv1n.us-west-2.rds.amazonaws.com:3306/mydb",key);
//        String encryptByAES_USER=encryptByAES("tiennv",key);
//        String encryptByAES_PASSWORD=encryptByAES("daicadung",key);
//        System.out.println(encryptByAES_URL);
//        System.out.println(encryptByAES_USER);
//        System.out.println(encryptByAES_PASSWORD);

        String encryptByAES_URL=encryptByAES("jdbc:mysql://localhost:3306/namtrun1_giaothong",key);
        String encryptByAES_USER=encryptByAES("namtrun1_giaothong",key);
        String encryptByAES_PASSWORD=encryptByAES("1234509876",key);
        System.out.println(encryptByAES_URL);
        System.out.println(encryptByAES_USER);
        System.out.println(encryptByAES_PASSWORD);

        System.out.println(encryptByAES("EAAOWuQMhG3EBAF7lpy41uvhzGDCKWuRQHD3bRfuW786sJ5dYZCnE54gtR17pHZBQ1rvaZAUD7zslihiMM4gfFU3xeZCFNfNi8NZBMEdKP5R2SrZCjcQf0KZA7YvHhDzqHUCRq10TV5cFigfiFvBZCjIsUiXDp2zo41nbMOXsWoBzOwZDZD",key));

    }
}
