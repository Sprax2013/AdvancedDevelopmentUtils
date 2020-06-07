package de.sprax2013.advanced_dev_utils.crypt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingUtils {
    /**
     * Gets the SHA-256 hash of a string using UTF-8
     *
     * @param string The String to hash
     *
     * @return The Hash in hex or null
     *
     * @see #getHash(String, String, Charset)
     */
    public static String getHash(String string) {
        return getHash(string, "SHA-256", StandardCharsets.UTF_8);
    }

    /**
     * Gets the SHA-256 hash of a string using a specific charset
     *
     * @param string   The String to hash
     * @param sCharset The charset to use for <i>string</i>
     *
     * @return The Hash in hex or null
     *
     * @see #getHash(String, String, Charset)
     */
    public static String getHash(String string, Charset sCharset) {
        return getHash(string, "SHA-256", sCharset);
    }

    /**
     * Gets the hash for a string using UTF-8
     *
     * @param string    The String to hash
     * @param algorithm The hashing algorithm to use
     *
     * @return The Hash in hex or null
     */
    public static String getHash(String string, String algorithm) {
        return getHash(string, algorithm, StandardCharsets.UTF_8);
    }

    /**
     * Gets the hash of a string using a specific charset and algorithm
     *
     * @param string    The String to hash
     * @param algorithm The hashing algorithm to use
     * @param sCharset  The charset to use for <i>string</i>
     *
     * @return The Hash in hex or null
     */
    public static String getHash(String string, String algorithm, Charset sCharset) {
        if (string != null) {
            try {
                return toHex(MessageDigest.getInstance(algorithm).digest(string.getBytes(sCharset)));
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Gets the SHA-256 hash for a file
     *
     * @param file The file to hash
     *
     * @return The Hash in hex or null
     */
    public static String getHash(File file) {
        return getHash(file, "SHA-256");
    }

    /**
     * Gets the hash for a file using a specific algorithm
     *
     * @param file      The file to hash
     * @param algorithm The hashing algorithm to use
     *
     * @return The Hash in hex or null
     */
    public static String getHash(File file, String algorithm) {
        if (file.exists() && file.isFile()) {
            try {
                MessageDigest digest = MessageDigest.getInstance(algorithm);
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

                byte[] buffer = new byte[8192];
                int count;
                while ((count = bis.read(buffer)) > 0) {
                    digest.update(buffer, 0, count);
                }
                bis.close();

                return toHex(digest.digest());
            } catch (NoSuchAlgorithmException | IOException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Turns a byte array into a hex string
     *
     * @param bytes The byte array
     *
     * @return The hex string
     */
    private static String toHex(byte[] bytes) {
        String result = "";

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);

            if (hex.length() == 1) {
                result += '0';
            }

            result += hex;
        }

        return result;
    }
}