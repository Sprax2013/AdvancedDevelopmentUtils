package de.sprax2013.advanced_dev_utils.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLAPI {
    private static HashMap<String, Connection> connections = new HashMap<>();

    /**
     * Gets a cached connection or creates a new one (and caches it)<br>
     * <br>
     * <b>autoReconnect is not set by this method</b>. If the host doesn't either,
     * it's recommended to use this method to get a valid cached connection or a new
     * one if it's not
     *
     * @param host     The host of the DB-server
     * @param port     The port to use
     * @param username The username to use
     * @param password The password to use
     * @param database The database to access
     *
     * @return The connection or null
     *
     * @throws ClassNotFoundException When the MySQL-Driver could not be found
     * @see #getConnection(String, int, String, String)
     */
    public static Connection getConnection(String host, int port, String username, String password, String database) {
        Connection con;

        synchronized (connections) {
            con = connections.get(host.toLowerCase() + port + username.toLowerCase() + database);
        }

        try {
            if (con == null || con.isClosed() || !con.isValid(15)) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException ignored) {
                    // Fallback: The Spigot-Module does not shade the driver - But on older Spigot-Servers they are
                    // still using the outdated one
                    Class.forName("com.mysql.jdbc.Driver");
                }

                con = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);

                synchronized (connections) {
                    connections.put(host.toLowerCase() + port + username.toLowerCase() + database, con);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return con;
    }

    /**
     * Gets the cached connection or null
     *
     * @param host     The host of the DB-server
     * @param port     The port to use
     * @param username The username to use
     * @param database The database to access
     *
     * @return The connection or null
     *
     * @see #getConnection(String, int, String, String, String)
     */
    public static Connection getConnection(String host, int port, String username, String database) {
        String key = host.toLowerCase() + port + username.toLowerCase() + database;
        Connection con = connections.get(key);

        if (con != null) {
            try {
                if (con.isClosed() || !con.isValid(15)) {
                    connections.remove(key);
                    con = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return con;
    }

    /**
     * Closes all connections and clears the cache
     */
    public static void deInit() {
        if (!connections.isEmpty()) {
            for (Connection con : connections.values()) {
                try {
                    if (con != null && !con.isClosed()) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            connections.clear();
        }
    }
}