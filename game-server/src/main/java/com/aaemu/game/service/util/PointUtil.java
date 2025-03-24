package com.aaemu.game.service.util;

/**
 * @author Shannon
 */
public class PointUtil {

    public static long convertX(double x) {
        return (long) (x * 4096) << 32;
    }

    public static long convertY(double y) {
        return (long) (y * 4096) << 32;
    }
}
