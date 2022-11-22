package net.easycloud.util;

import java.util.concurrent.ThreadLocalRandom;

public class StringUtil {

    private static final char[] CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    public static String generateString(int length) {

        StringBuilder s = new StringBuilder();

        for (int i = 0; i < length; i++) {
            s.append(CHARS[ThreadLocalRandom.current().nextInt(CHARS.length - 1)]);
        }

        return s.toString();
    }


}
