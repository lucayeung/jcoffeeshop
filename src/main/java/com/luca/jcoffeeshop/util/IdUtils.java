package com.luca.jcoffeeshop.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public final class IdUtils {

    /**
     * Generate short UUID (13 characters)
     */
    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }
}
