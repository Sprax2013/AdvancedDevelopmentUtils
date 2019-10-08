package de.sprax2013.advanced_dev_utils.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MACUtils {
    /**
     * Gets the System's MAC address.
     *
     * @return Value of
     * <code>MACUtils.getMACAddress(NetworkInterface.getByInetAddress(InetAddress.getLocalHost()))</code>
     */
    public static String getMACAddress() throws SocketException, UnknownHostException {
        return getMACAddress(NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
    }

    /**
     * @return The MAC-Address or null (
     * {@link NetworkInterface#getHardwareAddress()} )
     *
     * @throws SocketException      Info: {@link InetAddress#getLocalHost()}
     * @throws UnknownHostException Info: {@link NetworkInterface#getHardwareAddress()}
     */
    public static String getMACAddress(NetworkInterface netI) throws SocketException, UnknownHostException {
        StringBuilder builder = new StringBuilder();

        byte[] mac = netI.getHardwareAddress();

        for (int i = 0; i < mac.length; i++) {
            builder.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }

        return builder.toString();
    }
}