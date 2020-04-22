package de.sprax2013.advanced_dev_utils.mojang;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.sprax2013.advanced_dev_utils.misc.UUIDUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;

/**
 * This class contains methods to gather information of Mojang's public APIs.
 *
 * @see <a href="https://wiki.vg/Mojang_API">https://wiki.vg/Mojang_API</a>
 * @see <a href=
 * "https://gitlab.sprax2013.de/Sprax2013/api.sprax2013.de/wikis/home">https://gitlab.sprax2013.de/Sprax2013/api
 * .sprax2013.de/wikis/home</a>
 */
public class MojangAPI {
    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0";

    private static HashMap<String, CachedObject> profileCache = new HashMap<>();

    private static long lastSpraxAPIProblem = -1;
    private static int spraxAPIProblemCount = 0;

    /** Used for (de-)serialization of external API-Responses */
    static final Gson gson = new GsonBuilder().disableHtmlEscaping()
            .registerTypeAdapter(MojangHistory.class, new MojangHistoryDeSerializer())
            .registerTypeAdapter(MojangProfile.class, new MojangProfileDeSerializer())
            .registerTypeAdapter(MojangUUID.class, new MojangUUIDDeSerializer())
            .registerTypeAdapter(TextureProperty.class, new TexturePropertyDeSerializer()).create();

    /**
     * Returns all the usernames this user has used in the past and the one they are
     * using currently.<br>
     * <br>
     * This method contacts <i>api.sprax2013.de</i>. If it is not available it uses
     * the <i>Mojang-API</i> as a fallback instead.
     *
     * @param uuid The UUID
     *
     * @return {@link MojangHistory}-Object or null
     *
     * @see #getNameHistory(String)
     */
    public static MojangHistory getNameHistory(UUID uuid) {
        return getNameHistory(uuid != null ? uuid.toString() : null);
    }

    /**
     * Returns all the usernames this user has used in the past and the one they are
     * using currently.<br>
     * <br>
     * This method contacts <i>api.sprax2013.de</i>. If it is not available it uses
     * the <i>Mojang-API</i> as a fallback instead.<br>
     * <br>
     * <b>Using the username causes two external API-Requests. Using UUID if
     * available is recommended.</b>
     *
     * @param username The username (or the UUID as a string)
     *
     * @return {@link MojangHistory}-Object or null
     *
     * @see #getNameHistory(UUID)
     */
    public static MojangHistory getNameHistory(String username) {
        MojangHistory result = null;

        if (username != null && !username.isEmpty()) {
            try {
                Response res = Jsoup
                        .connect("https://api.sprax2013.de/mojang/history/" + URLEncoder.encode(username, "UTF-8"))
                        .userAgent(USER_AGENT)
                        .ignoreContentType(true).execute();

                if (res.statusCode() == 200) {
                    result = gson.fromJson(res.body(), MojangHistory.class);
                }
            } catch (SocketTimeoutException ignore) {
                System.err.println("MojangAPI: Connection to 'api.sprax2013.de' timed out");
                incrementSpraxAPIProblem();
            } catch (Exception ex) {
                ex.printStackTrace();

                incrementSpraxAPIProblem();
            }

            if (result == null) {
                UUID uuid = null;

                if (username.length() > 16) {
                    try {
                        uuid = UUID.fromString(UUIDUtils.addDashesToUUID(username));
                    } catch (Exception ignore) {
                    }
                } else {
                    MojangUUID uuidObj = getUUID(username);

                    if (uuidObj != null) {
                        uuid = uuidObj.getUUID();
                    }
                }

                if (uuid != null) {
                    try {
                        Response res = Jsoup
                                .connect("https://api.mojang.com/user/profiles/"
                                        + URLEncoder.encode(uuid.toString().replace("-", ""), "UTF-8") + "/names")
                                .userAgent(USER_AGENT)
                                .ignoreContentType(true).execute();

                        if (res.statusCode() == 200) {
                            result = gson.fromJson(res.body(), MojangHistory.class);
                        }
                    } catch (SocketTimeoutException ignore) {
                        System.err.println("MojangAPI: Connection to 'api.mojang.com' timed out");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        return result;
    }

    /**
     * This will return the player's username plus any additional information about
     * them (e.g. skins).<br>
     * <br>
     * This method contacts <i>api.sprax2013.de</i>. If it is not available it uses
     * the <i>Mojang-API</i> as a fallback instead.<br>
     * <br>
     * <b>Using the username can cause two external API-Requests. Using UUID if
     * available is recommended.</b><br>
     * <b>This Method caches results for 60 seconds!</b>
     *
     * @param uuid The UUID
     *
     * @return {@link MojangProfile}-Object or null
     *
     * @see #getProfile(String)
     */
    public static MojangProfile getProfile(UUID uuid) {
        return getProfile(uuid != null ? uuid.toString() : null);
    }

    /**
     * This will return the player's username plus any additional information about
     * them (e.g. skins).<br>
     * <br>
     * This method contacts <i>api.sprax2013.de</i>. If it is not available it uses
     * the <i>Mojang-API</i> as a fallback instead.<br>
     * <br>
     * <b>Using the username can cause two external API-Requests. Using UUID if
     * available is recommended.</b><br>
     * <b>This Method caches results for 60 seconds!</b>
     *
     * @param username The username (or the UUID as a string)
     *
     * @return {@link MojangProfile}-Object or null
     *
     * @see #getProfile(UUID)
     */
    public static MojangProfile getProfile(String username) {
        MojangProfile result = null;

        if (username != null && !username.isEmpty()) {
            CachedObject cached = profileCache.get(username.toLowerCase());

            if (cached == null || !cached.isValid()) {
                if (cached != null) {
                    profileCache.remove(cached.getAsProfile().getUUID().toString());
                    profileCache.remove(cached.getAsProfile().getUsername().toLowerCase());
                }

                try {
                    Response res = Jsoup
                            .connect("https://api.sprax2013.de/mc/profile/" + URLEncoder.encode(username, "UTF-8"))
                            .userAgent(USER_AGENT)
                            .ignoreContentType(true).execute();

                    if (res.statusCode() == 200) {
                        result = gson.fromJson(res.body(), MojangProfile.class);

                        cached = new CachedObject(result);
                        profileCache.put(result.getUUID().toString(), cached);
                        profileCache.put(result.getUsername().toLowerCase(), cached);
                    }
                } catch (SocketTimeoutException ignore) {
                    System.err.println("MojangAPI: Connection to 'api.sprax2013.de' timed out");
                    incrementSpraxAPIProblem();
                } catch (Exception ex) {
                    ex.printStackTrace();

                    incrementSpraxAPIProblem();
                }

                if (result == null) {
                    UUID uuid = null;

                    if (username.length() > 16) {
                        try {
                            uuid = UUID.fromString(UUIDUtils.addDashesToUUID(username));
                        } catch (Exception ignore) {
                        }
                    } else {
                        MojangUUID uuidObj = getUUID(username);

                        if (uuidObj != null) {
                            uuid = uuidObj.getUUID();
                        }
                    }

                    if (uuid != null) {
                        try {
                            Response res = Jsoup.connect("https://sessionserver.mojang.com/session/minecraft/profile/"
                                    + URLEncoder.encode(uuid.toString().replace("-", ""), "UTF-8") + "?unsigned=false")
                                    .userAgent(USER_AGENT)
                                    .ignoreContentType(true).execute();

                            if (res.statusCode() == 200) {
                                result = gson.fromJson(res.body(), MojangProfile.class);

                                cached = new CachedObject(result);
                                profileCache.put(result.getUUID().toString(), cached);
                                profileCache.put(result.getUsername().toLowerCase(), cached);
                            }
                        } catch (SocketTimeoutException ignore) {
                            System.err.println("MojangAPI: Connection to 'sessionserver.mojang.com' timed out");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                result = cached.getAsProfile();
            }
        }

        return result;
    }

    /**
     * This will return the UUID of the name.<br>
     * <br>
     * This method contacts <i>api.sprax2013.de</i>. If it is not available it uses
     * the <i>Mojang-API</i> as a fallback instead.<br>
     *
     * @param username The username
     *
     * @return {@link MojangUUID}-Object or null
     */
    public static MojangUUID getUUID(String username) {
        MojangUUID result = null;

        if (username != null && !username.isEmpty()) {
            if (!skipSpraxAPI()) {
                try {
                    Response res = Jsoup
                            .connect("https://api.sprax2013.de/mc/uuid/" + URLEncoder.encode(username, "UTF-8"))
                            .userAgent(USER_AGENT)
                            .timeout(5_000).ignoreContentType(true).execute();

                    if (res.statusCode() == 200) {
                        result = gson.fromJson(res.body(), MojangUUID.class);
                    }
                } catch (SocketTimeoutException ignore) {
                    System.err.println("MojangAPI: Connection to 'api.sprax2013.de' timed out");
                    incrementSpraxAPIProblem();
                } catch (Exception ex) {
                    ex.printStackTrace();

                    incrementSpraxAPIProblem();
                }
            }

            if (result == null) {
                try {
                    Response res = Jsoup
                            .connect("https://api.mojang.com/users/profiles/minecraft/"
                                    + URLEncoder.encode(username, "UTF-8"))
                            .userAgent(USER_AGENT)
                            .ignoreContentType(true).timeout(15_000).execute();

                    if (res.statusCode() == 200) {
                        result = gson.fromJson(res.body(), MojangUUID.class);
                    }
                } catch (@SuppressWarnings("unused") SocketTimeoutException ignore) {
                    System.err.println("MojangAPI: Connection to 'api.mojang.com' timed out");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return result;
    }

    private static void incrementSpraxAPIProblem() {
        if ((System.currentTimeMillis() - lastSpraxAPIProblem) > 180_000) {
            spraxAPIProblemCount = 0;
        }

        spraxAPIProblemCount++;
        lastSpraxAPIProblem = System.currentTimeMillis();
    }

    private static boolean skipSpraxAPI() {
        return spraxAPIProblemCount > 3 && (System.currentTimeMillis() - lastSpraxAPIProblem) <= 180_000; // 3min
    }
}

class CachedObject {
    private final Object obj;
    private final long millis;
    private final int ttl = 62 * 1_000;

    CachedObject(Object obj) {
        this.obj = obj;
        this.millis = System.currentTimeMillis();
    }

    public Object getObject() {
        return this.obj;
    }

    boolean isValid() {
        return (System.currentTimeMillis() - this.millis) <= ttl;
    }

    MojangProfile getAsProfile() {
        return this.obj instanceof MojangProfile ? (MojangProfile) this.obj : null;
    }
}
