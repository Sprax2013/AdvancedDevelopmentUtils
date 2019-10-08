package de.sprax2013.advanced_dev_utils;

import de.sprax2013.advanced_dev_utils.mineskin.MineSkinClient;
import de.sprax2013.advanced_dev_utils.mysql.MySQLAPI;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static Set<MineSkinClient> mineSkinClients = new HashSet<>();

    public static void init() {
    }

    public static void deInit() {
        for (MineSkinClient msC : mineSkinClients) {
            try {
                msC.destroy();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        MySQLAPI.deInit();
    }
}