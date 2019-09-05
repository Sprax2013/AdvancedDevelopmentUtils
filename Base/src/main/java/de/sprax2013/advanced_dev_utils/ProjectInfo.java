package de.sprax2013.advanced_dev_utils;

import java.io.IOException;
import java.util.Properties;

public class ProjectInfo {
    /**
     * @return Project-Name from file '/project'
     */
    public static String getName() {
        try {
            Properties prop = new Properties();
            prop.load(ProjectInfo.class.getResourceAsStream("/project"));

            return prop.getProperty("name");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * @return Project-Version from file '/project'
     */
    public static String getVersion() {
        try {
            Properties prop = new Properties();
            prop.load(ProjectInfo.class.getResourceAsStream("/project"));

            return prop.getProperty("version");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * @return Project-VersionCode from file '/project'
     */
    public static int getVersionCode() {
        try {
            Properties prop = new Properties();
            prop.load(ProjectInfo.class.getResourceAsStream("/project"));

            return Integer.parseInt(prop.getProperty("versionCode"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return -1;
    }
}