package de.sprax2013.advanced_dev_utils.spigot.utils.files;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;

import java.io.File;

/**
 * This class will help you extracting and creating ZIP-Archives.<br>
 * You can even auto-extract a Minecraft-World from a ZIP-Archive or auto-create
 * a ZIP-Archive from a Minecraft-World
 */
public class ZipUtils {
    /**
     * Will extract a Zip-Archive into Bukkit's WorldContainer.<br>
     * This requires a specific file/folder layout:<br>
     * ├── <b>level.dat</b><br>
     * ├── ...<br>
     * ├── <b>region</b><br>
     * │ ├── r.0.0.mca<br>
     * Note: It's important that the world files are in the root-dir of the archive.
     * This Methode will check for the level.dat. There can be even more files and
     * directories. They will be extracted as well no matter if they are part of the
     * world or not
     *
     * @param zipArchive the ZIP-Archive
     *
     * @return true, if successful
     */
    public static boolean extractWorldZipArchive(File zipArchive) {
        return extractWorldZipArchive(zipArchive, null);
    }

    /**
     * Will extract a Zip-Archive into Bukkit's WorldContainer.<br>
     * This requires a specific file/folder layout:<br>
     * ├── <b>level.dat</b><br>
     * ├── ...<br>
     * ├── <b>region</b><br>
     * │ ├── r.0.0.mca<br>
     * Note: It's important that the world files are in the root-dir of the archive.
     * This Methode will check for the level.dat. There can be even more files and
     * directories. They will be extracted as well no matter if they are part of the
     * world or not
     *
     * @param zipArchive  the ZIP-Archive
     * @param zipPassword the password of the archive
     *
     * @return true, if successful
     */
    public static boolean extractWorldZipArchive(File zipArchive, String zipPassword) {
        try {
            ZipFile zip = new ZipFile(zipArchive);

            if (zip.isValidZipFile()) {
                if (zip.isEncrypted() && zipPassword != null) {
                    zip.setPassword(zipPassword.toCharArray());
                }

                boolean validWorldArchive = false;

                for (Object obj : zip.getFileHeaders()) {
                    FileHeader fH = (FileHeader) obj;
                    if (fH.getFileName().equalsIgnoreCase("level.dat")) {
                        validWorldArchive = true;
                        break;
                    }
                }

                if (validWorldArchive) {
                    File destDir = new File(Bukkit.getWorldContainer().getAbsolutePath(),
                            FilenameUtils.getBaseName(zipArchive.getName()));

                    return extractZipArchive(zipArchive, destDir, zipPassword);
                }
            }
        } catch (ZipException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Extracts all files and directories from a ZIP-Archive.<br>
     * <b>Note: Existing Files will be overwritten!</b>
     *
     * @param zipArchive the ZIP-Archive
     * @param destDir    the destination of the archive's content
     *
     * @return true, if successful
     */
    public static boolean extractZipArchive(File zipArchive, File destDir) {
        return extractZipArchive(zipArchive, destDir, null);
    }

    /**
     * Extracts all files and directories from a ZIP-Archive.<br>
     * <b>Note: Existing Files will be overwritten!</b>
     *
     * @param zipArchive  the ZIP-Archive
     * @param destDir     the destination of the archive's content
     * @param zipPassword the password of the archive
     *
     * @return true, if successful
     */
    public static boolean extractZipArchive(File zipArchive, File destDir, String zipPassword) {
        try {
            ZipFile zip = new ZipFile(zipArchive);

            if (zip.isValidZipFile()) {
                if (zip.isEncrypted() && zipPassword != null) {
                    zip.setPassword(zipPassword.toCharArray());
                }

                for (Object obj : zip.getFileHeaders()) {
                    FileHeader fH = (FileHeader) obj;

                    zip.extractFile(fH, destDir.getAbsolutePath());
                }

                return true;
            }
        } catch (ZipException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean createZipArchive(File src, File destFile, String zipPassword) {
        ZipParameters zipParams = new ZipParameters();

        zipParams.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParams.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        if (zipPassword != null && !zipPassword.isEmpty()) {
            zipParams.setEncryptFiles(true);
            zipParams.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            zipParams.setPassword(zipPassword.toCharArray());
        }

        try {
            ZipFile zip = new ZipFile(destFile);

            if (zipPassword != null) {
                zip.setPassword(zipPassword.toCharArray());
            }

            if (src.isDirectory()) {
                zip.addFolder(src, zipParams);
            } else {
                zip.addFile(src, zipParams);
            }

            return true;
        } catch (ZipException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}