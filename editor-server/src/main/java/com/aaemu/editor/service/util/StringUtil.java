package com.aaemu.editor.service.util;

import java.util.HexFormat;

/**
 * @author Shannon
 */
public class StringUtil {

    public static String toHex(String string) {
        return HexFormat.of().formatHex(string.getBytes());
    }

    public static String toString(CharSequence hex) {
        return new String(HexFormat.of().parseHex(hex));
    }
}