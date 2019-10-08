package de.sprax2013.advanced_dev_utils.crypt;

import java.util.Base64;

public class StringCryptUtils {
    // TODO

    @Deprecated
    public static final int CAESAR_OFFSET = 4;

    /**
     * <b><u><font color="red">BROKEN</font></u></b><br>
     * <p>
     * This encodes the String as Base64, reverses it and applies a simple
     * <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Caesar cipher</a>!<br>
     * <br>
     * '<u>The Caesar cipher can be easily broken even in a ciphertext-only
     * scenario.</u>' (<a href=
     * "https://en.wikipedia.org/wiki/Caesar_cipher#Breaking_the_cipher">Source</a>
     * Last access: 02.07.2018 - 12:28 EU/Berlin)
     *
     * @param s The string
     *
     * @return The encrypted String
     *
     * @see #easyDecrypt(String)
     */
    @Deprecated
    public String easyEncrypt(String s) {
        String reverse = new StringBuffer(new String(Base64.getEncoder().encode(s.getBytes()))).reverse().toString();

        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < reverse.length(); i++) {
            tmp.append(reverse.charAt(i) + CAESAR_OFFSET);
        }
        return tmp.toString();
    }

    /**
     * <b><u><font color="red">BROKEN</font></u></b><br>
     * <p>
     * This decodes the String using a simple
     * <a href="https://en.wikipedia.org/wiki/Caesar_cipher">Caesar cipher</a>,
     * reversing it and decoding it as Base64!<br>
     * <br>
     * '<u>The Caesar cipher can be easily broken even in a ciphertext-only
     * scenario.</u>' (<a href=
     * "https://en.wikipedia.org/wiki/Caesar_cipher#Breaking_the_cipher">Source</a>
     * Last access: 02.07.2018 - 12:28 EU/Berlin)
     *
     * @param s The string
     *
     * @return The encrypted String
     *
     * @see #easyEncrypt(String)
     */
    @Deprecated
    public String easyDecrypt(String s) {
        StringBuilder tmp = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            tmp.append(s.charAt(i) - CAESAR_OFFSET);
        }

        return new String(Base64.getDecoder().decode(new StringBuffer(tmp.toString()).reverse().toString()));
    }
}