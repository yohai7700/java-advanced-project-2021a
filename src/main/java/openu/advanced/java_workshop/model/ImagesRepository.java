package openu.advanced.java_workshop.model;

import org.primefaces.shaded.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ImagesRepository {
    private static final String DEFAULT_DRIVE = "C";

    private static String getImagesDrive() {
        String hardDriveEnv = System.getenv("drive");
        if (hardDriveEnv == null) {
            return DEFAULT_DRIVE;
        }
        return hardDriveEnv;
    }

    private static String getImagesBasePath() {
        return getImagesDrive() + ":\\java_workshop\\images\\";
    }

    private static String getImagePath(String path) {
        return getImagesBasePath() + path + ".jpg";
    }

    public static void uploadImage(String path, InputStream inputStream) throws IOException {
        File file = new File(getImagePath(path));
        file.mkdirs();
        file.createNewFile();
        Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        inputStream.close();
    }

    public static InputStream retrieveImage(String path) throws IOException {
        InputStream fileInputStream = new FileInputStream(getImagePath(path));
        byte[] bytes = IOUtils.toByteArray(fileInputStream);
        fileInputStream.close();
        return new ByteArrayInputStream(bytes);
    }

    public static boolean isExists(String path){
        return new File(getImagePath(path)).exists();
    }
}
