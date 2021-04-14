package openu.advanced.java_workshop.model;

import org.primefaces.shaded.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Gives the web pages access to the images of the games (in webapp/resources/images)
 */

public class ImagesRepository {
    private static final String DEFAULT_DRIVE = "C:";

    /**
     * Returns the drive which holds the project (and therefore all the images)
     * @return the string representing the drive (will be used to construct the path to the images)
     */
    private static String getImagesDrive() {
        String hardDriveEnv = System.getenv("drive");
        if (hardDriveEnv == null) {
            return DEFAULT_DRIVE;
        }
        return hardDriveEnv;
    }

    /**
     * Returns the path to images directory
     * @return the absolute path to images directory
     */
    private static String getImagesBasePath() {
        return getImagesDrive() + "\\java_workshop\\images\\";
    }

    /**
     * Gets the absolute path to the requested image
     * @param path the relative path to the requested image
     * @return the absolute path to the image
     */
    private static String getImagePath(String path) {
        return getImagesBasePath() + path + ".jpg";
    }

    /**
     * Adds a new image to the images directory
     * @param path the relative path of the image we want to add
     * @param inputStream holds the stream of data for the image
     * @throws IOException in case the stream has an exception
     */
    public static void uploadImage(String path, InputStream inputStream) throws IOException {
        // Creates a file in the given path
        File file = new File(getImagePath(path));
        file.mkdirs();
        file.createNewFile();

        // Copies the image to the file
        Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        inputStream.close();
    }

    /**
     * Transfers an image as a Stream of a byte array
     * @param path the path of the image
     * @return an ByteArrayInputStream that holds the byte array of the picture
     * @throws IOException in case an IO function has an exception
     */
    public static InputStream retrieveImage(String path) throws IOException {
        // Transforms the image into an input stream and then to a byte array
        InputStream fileInputStream = new FileInputStream(getImagePath(path));
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        fileInputStream.close();
        return new ByteArrayInputStream(bytes); // Returns a stream with the byte array
    }

    /**
     * Checks if there's a image in the given path
     * @param path the path to check for existence
     * @return true if the path exists and false otherwise
     */
    public static boolean isExists(String path) {
        return new File(getImagePath(path)).exists();
    }
}
