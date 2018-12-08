package sample;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Libraries {

    public void loadAndRun(){
        setDllPath();
        loadProjectLibraries();

        String path = "C:\\Users\\wh11t\\IdeaProjects\\ResearchWork\\raw";
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(path);

        simpleDLL simpleDll = simpleDLL.INSTANCE;
        simpleDLL imgproc = simpleDLL.INSTANCE;
        simpleDLL core = simpleDLL.INSTANCE;

        Mat picture = simpleDll.opendcm(path);
        Imgcodecs.imwrite("raw\\image2.jpg", picture);
//        opencvVideoCapture();
    }

    public interface simpleDLL extends Library {
        simpleDLL INSTANCE = (simpleDLL) Native.loadLibrary(
                (Platform.isWindows() ? "simpleDLL" : "simpleDLLLinuxPort"), simpleDLL.class);

        Mat opendcm(String pic);
        void intec(String s);
    }

    private static void setDllPath() {
        String myLibraryPath = System.getProperty("user.dir");
        System.setProperty("java.library.path", myLibraryPath);
    }

    private static void loadProjectLibraries() {
        System.loadLibrary("simpleDLL");
        System.loadLibrary("opencv_java343");
//        System.loadLibrary("vcruntime140");
        System.loadLibrary("opencv_core340");
        System.loadLibrary("opencv_imgproc340");
//        System.loadLibrary("opencv_imgcodecs340");
//        System.loadLibrary("opencv_highgui340");
    }

    private static void opencvVideoCapture(){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture camera = new VideoCapture(0);

        if(!camera.isOpened()){
            System.out.println("Error");
        }else{
            Mat frame = new Mat();
            while(true){
                if(camera.read(frame)){
                    System.out.println("Frame Obtained");
                    System.out.println("Captured frame width "+frame.width()+" height " + frame.height());
                    Imgcodecs.imwrite("raw\\camera.jpg", frame);
                    System.out.print("OK");
                    break;
                }
            }
        }
        camera.release();
    }
}
