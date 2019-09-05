package de.sprax2013.advanced_dev_utils.network.ping;

import java.net.UnknownHostException;
import java.util.List;

public abstract class MinecraftResponse {
    private String error;

    public Version version;
    public Players players;
    private String favicon;

    private String rawResponse;

    private String host;

    public MinecraftResponse() {
    }

    public MinecraftResponse(String host) {
        this.host = host;
    }

    public abstract String getMotd();

    /**
     * <b>Do not use this method</b><br>
     * <b>It will be removed in the future!</b>
     */
    public MinecraftResponse setRawResponse(String rawResponse) {
        if (this.rawResponse == null) {
            this.rawResponse = rawResponse;
        }

        return this;
    }

    /**
     * <b>Do not use this method</b><br>
     * <b>It will be removed in the future!</b>
     */
    public MinecraftResponse setHost(String host) {
        if (this.host == null) {
            this.host = host;
        }

        return this;
    }

    /**
     * <b>Do not use this method</b><br>
     * <b>It will be removed in the future!</b>
     */
    public MinecraftResponse setError(String error) {
        if (this.error == null) {
            this.error = error;
        }

        return this;
    }

    /**
     * @return true, if is online - false if offline (or failed)
     */
    public boolean isOnline() {
        return host != null && version != null && getMotd() != null;
    }

    /**
     * @return favicon encoded in <i>Base64</i>
     */
    public String getFavicon() {
        return favicon;
    }

    /**
     * Typically in JSON
     *
     * @return the response
     */
    public String getRawResponse() {
        return rawResponse;
    }

    /**
     * Uses {@link PingUtils#getRTT(String, int)} to determine the <i>RTT</i> <br>
     * <u>The returned value is not cached!</u>
     *
     * @return rtt in ms or <code>-1</code> when offline
     */
    public int getRTT() {
        if (isOnline()) {
            try {
                return PingUtils.getRTT(host, 2500);
            } catch (@SuppressWarnings("unused") UnknownHostException ignore) {
            }
        }

        return -1;
    }

    public class Version {
        private String name;
        private int protocol;

        /**
         * Gets the Version-Name.<br>
         * Vanilla-Server will return the versions name (like '1.12.2').<br>
         * This Value is often modified by non Vanilla-Server.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the protocols version.
         */
        public int getVersion() {
            return protocol;
        }
    }

    public class Players {
        private int max;
        private int online;

        private List<Player> sample;

        /**
         * @return The max. player count (Slot count)
         */
        public int getMax() {
            return max;
        }

        /**
         * @return The online player count
         */
        public int getOnline() {
            return online;
        }

        /**
         * @return A list of players that are on the server (If the Server is
         * broadcasting them).
         */
        public List<Player> getSample() {
            return sample;
        }
    }

    public class Player {
        private String name;
        private String id;

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @return The uuid
         */
        public String getUUID() {
            return id;
        }
    }

    public class Description {
        public String text;
    }
}