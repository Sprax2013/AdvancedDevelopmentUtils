package de.sprax2013.advanced_dev_utils.network.ping;

import com.google.gson.Gson;
import de.sprax2013.advanced_dev_utils.network.DNSUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class PingUtils {
    // RTT

    /**
     * @return Value of <code>PingUtils.getRTT(<b>host</b>, 2500)</code>
     *
     * @see #getRTT(String, int)
     */
    public static int getRTT(String host) throws UnknownHostException {
        return getRTT(host, 2500);
    }

    /**
     * Pings a <b>host</b> and returns it's RTT (<i>Round-Trip Time</i>: <a href=
     * "https://en.wikipedia.org/wiki/Round-trip_delay_time">https://en.wikipedia.org/wiki/Round-trip_delay_time</a>)
     *
     * @param host    the host
     * @param timeout The timeout in milliseconds
     *
     * @return The RTT in ms or <b>-1</b> in case of timeout
     *
     * @throws UnknownHostException     If no IP address for <b>host</b> could be
     *                                  found, or if a scope_id was specified for a
     *                                  global IPv6 address.
     * @throws SecurityException        If a security manager exists and its
     *                                  checkConnect method doesn't allow the
     *                                  operation
     * @throws IllegalArgumentException If <b>timeout</b> is negative
     */
    public static int getRTT(String host, int timeout) throws UnknownHostException {
        InetAddress inet = InetAddress.getByName(host);

        long start = System.currentTimeMillis();

        try {
            if (inet.isReachable(timeout)) {
                return Long.valueOf(System.currentTimeMillis() - start).intValue();
            }
        } catch (@SuppressWarnings("unused") IOException ignore) {
        }

        return -1;
    }

    // Ping - Minecraft

    /**
     * @return Value of
     * <code>PingUtils.pingServiceMinecraft(<b>host</b>, 25565)</code>
     *
     * @see #pingServiceMinecraft(String, int)
     */
    public static MinecraftResponse pingServiceMinecraft(String host) {
        return pingServiceMinecraft(host, 25565);
    }

    /**
     * @return Value of
     * <code>PingUtils.pingServiceMinecraft(<b>host</b>, <b>ping</b>, 25565)</code>
     *
     * @see #pingServiceMinecraft(String, int, int)
     */
    public static MinecraftResponse pingServiceMinecraft(String host, int port) {
        return pingServiceMinecraft(host, port, 2500);
    }

    /**
     * This method will automatically use
     * {@link DNSUtils#lookupDNSRecord_SRV(String, String, String)} to get the
     * matching SRV-Record of a domain.<br>
     *
     * @return Value of {@link #pingServiceMinecraft(InetSocketAddress, int)}
     *
     * @see #pingServiceMinecraft(InetSocketAddress, int)
     */
    public static MinecraftResponse pingServiceMinecraft(String host, int port, int timeout) {
        String dnsLookup = DNSUtils.lookupDNSRecord_SRV(host, "minecraft", "tcp");

        if (dnsLookup != null) {
            String[] dnsArgs = dnsLookup.split(":");

            return pingServiceMinecraft(new InetSocketAddress(dnsArgs[0], Integer.parseInt(dnsArgs[1])), timeout);
        }

        return pingServiceMinecraft(new InetSocketAddress(host, port), timeout);
    }

    // TODO Pre 1.7 support, Code optimieren, die ganzen mini-kack-Klassen aus
    // dieser hier nehmen!

    /**
     * Sends a Handshake-Paket to a Minecraft-Server and returns a
     * <i>MinecraftResponse</i>-Object with information like <i>Motd</i> or
     * <i>Protocol</i>.<br>
     * <u><b>ATTENTION: Only (Vanilla-)Server from 1.7 and up will succeed. Legacy
     * support is planned but not prioritized. You can open a GitHub-Issue if you
     * want this method to be updated :)</b></u><br>
     * <br>
     * {@link #pingServiceMinecraft(String, int, int)} or
     * {@link #pingServiceMinecraft(String, int)}
     *
     * @return A MinecraftResponse-Object. <u><b>Never <code>null</code></b></u><br>
     * Use method {@link MinecraftResponse#getError()} or
     * {@link MinecraftResponse#isOnline()}.
     *
     * @author jamietech (<a href=
     * "https://github.com/jamietech/MinecraftServerPing">https://github.com/jamietech/MinecraftServerPing</a>)
     */
    public static MinecraftResponse pingServiceMinecraft(InetSocketAddress addr, int timeout) {
        String error = null;

        Socket socket = new Socket();
        try {
            socket.setSoTimeout(timeout);
            socket.connect(addr);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // > Handshake

            ByteArrayOutputStream handshake_bytes = new ByteArrayOutputStream();
            DataOutputStream handshake = new DataOutputStream(handshake_bytes);

            handshake.writeByte(MinecraftServiceUtils.PACKET_HANDSHAKE);
            MinecraftServiceUtils.writeVarInt(handshake, MinecraftServiceUtils.PROTOCOL_VERSION);
            MinecraftServiceUtils.writeVarInt(handshake, addr.getHostString().length());
            handshake.writeBytes(addr.getHostString());
            handshake.writeShort(addr.getPort());
            MinecraftServiceUtils.writeVarInt(handshake, MinecraftServiceUtils.STATUS_HANDSHAKE);

            MinecraftServiceUtils.writeVarInt(out, handshake_bytes.size());
            out.write(handshake_bytes.toByteArray());

            // > Status request

            out.writeByte(0x01); // Size of packet
            out.writeByte(MinecraftServiceUtils.PACKET_STATUSREQUEST);

            // < Status response

            MinecraftServiceUtils.readVarInt(in); // Size
            int id = MinecraftServiceUtils.readVarInt(in);

            String srvResponse = null;

            if (id == -1) {
                error = "Server prematurely ended stream.";
            } else if (id != MinecraftServiceUtils.PACKET_STATUSREQUEST) {
                error = "Server returned invalid packet.";
            }

            if (error == null) {
                try {
                    int length = MinecraftServiceUtils.readVarInt(in);

                    if (length == -1) {
                        error = "Server prematurely ended stream.";
                    } else if (length == 0) {
                        error = "Server returned unexpected value.";
                    }

                    if (error == null) {
                        byte[] data = new byte[length];
                        in.readFully(data);
                        srvResponse = new String(data, "UTF-8");

                        // > Ping

                        out.writeByte(0x09); // Size of packet
                        out.writeByte(MinecraftServiceUtils.PACKET_PING);
                        out.writeLong(System.currentTimeMillis());

                        // < Ping

                        MinecraftServiceUtils.readVarInt(in); // Size
                        id = MinecraftServiceUtils.readVarInt(in);

                        if (id == -1) {
                            error = "Server prematurely ended stream.";
                        } else if (id != MinecraftServiceUtils.PACKET_PING) {
                            error = "Server returned invalid packet.";
                        }
                    }
                } catch (@SuppressWarnings("unused") Throwable th) {
                }
            }

            // Close

            try {
                handshake.close();
            } catch (@SuppressWarnings("unused") Exception ex) {
            }

            try {
                handshake_bytes.close();
            } catch (@SuppressWarnings("unused") Exception ex) {
            }

            try {
                out.close();
            } catch (@SuppressWarnings("unused") Exception ex) {
            }

            try {
                in.close();
            } catch (@SuppressWarnings("unused") Exception ex) {
            }

            try {
                socket.close();
            } catch (@SuppressWarnings("unused") Exception ex) {
            }

            if (srvResponse != null) {
                if (srvResponse.contains("\"description\":{") && srvResponse.contains("\"text\":\"")) {
                    return new Gson().fromJson(srvResponse, MinecraftServerResponse.class).setError(error)
                            .setRawResponse(srvResponse).setHost(addr.getHostString());
                }

                return new Gson().fromJson(srvResponse, MinecraftServerResponse_Pre1_8.class).setError(error)
                        .setRawResponse(srvResponse).setHost(addr.getHostString());
            }
        } catch (@SuppressWarnings("unused") Throwable th) {
            // Server is offline
        }

        return new MinecraftServerResponse().setError(error);
    }
}

class MinecraftServerResponse_Pre1_8 extends MinecraftResponse {
    public MinecraftServerResponse_Pre1_8() {
    }

    public MinecraftServerResponse_Pre1_8(String host) {
        super(host);
    }

    private String description;

    @Override
    public String getMotd() {
        return description;
    }
}

class MinecraftServerResponse extends MinecraftResponse {
    public MinecraftServerResponse() {
    }

    public MinecraftServerResponse(String host) {
        super(host);
    }

    private Description description;

    @Override
    public String getMotd() {
        return description.text;
    }
}

class MinecraftServiceUtils {
    public static final byte PACKET_HANDSHAKE = 0x00, PACKET_STATUSREQUEST = 0x00, PACKET_PING = 0x01;
    public static final int PROTOCOL_VERSION = 4;
    public static final int STATUS_HANDSHAKE = 1;

    /**
     * @throws IOException
     * @author Thinkofname (<a href=
     * "https://gist.github.com/Thinkofname/e975ddee04e9c87faf22">https://gist.github
     * .com/Thinkofname/e975ddee04e9c87faf22</a>)
     */
    public static int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;

        while (true) {
            int k = in.readByte();

            i |= (k & 0x7F) << j++ * 7;

            if (j > 5)
                throw new RuntimeException("VarInt too big");

            if ((k & 0x80) != 128)
                break;
        }

        return i;
    }

    /**
     * @author Thinkofname (<a href=
     * "https://gist.github.com/Thinkofname/e975ddee04e9c87faf22">https://gist.github
     * .com/Thinkofname/e975ddee04e9c87faf22</a>)
     */
    public static void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }

            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }
}