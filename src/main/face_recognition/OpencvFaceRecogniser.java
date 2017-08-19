
package main.face_recognition;

import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.IntBuffer;
/*     */ import main.utils.FileLocations;
import org.bytedeco.javacpp.DoublePointer;
/*     */ import org.bytedeco.javacpp.IntPointer;
/*     */ import org.bytedeco.javacpp.opencv_core;
/*     */ import org.bytedeco.javacpp.opencv_core.Mat;
/*     */ import org.bytedeco.javacpp.opencv_core.MatVector;
/*     */ import org.bytedeco.javacpp.opencv_face;
/*     */ import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
/*     */ import org.bytedeco.javacpp.opencv_imgcodecs;

import static main.utils.Constants.CONFIDENCE_LEVEL;
 public class OpencvFaceRecogniser{
    public static FaceRecognizer faceRecognizer;

    public static void trainRecognizer(){
        String trainingDir = FileLocations.TRAINING_DATA;
        File root = new File(trainingDir);
        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return (name.endsWith(".jpg")) || (name.endsWith(".pgm")) || (name.endsWith(".png"));
            }
        };
        File[] imageFiles = root.listFiles(imgFilter);
        if (imageFiles.length > 0){
            MatVector images = new MatVector(imageFiles.length);
            Mat labels = new Mat(imageFiles.length, 1, opencv_core.CV_32SC1);
            IntBuffer labelsBuf = (IntBuffer) labels.createBuffer();
            int counter = 0;
            for (File image : imageFiles) {
                Mat img = opencv_imgcodecs.imread(image.getAbsolutePath(), 0);
                int label = Integer.parseInt(image.getName().split("\\-")[0]);
                images.put(counter, img);
                labelsBuf.put(counter, label);
                counter++;
            }
            faceRecognizer = opencv_face.createLBPHFaceRecognizer();
            faceRecognizer.train(images, labels);
        }
    }

    public static int predictedLabel(String testImagePath){
        Mat testImage = opencv_imgcodecs.imread(testImagePath, 0);
        IntPointer label = new IntPointer(1L);
        DoublePointer confidence = new DoublePointer(1L);
        faceRecognizer.predict(testImage, label, confidence);
        int predictedLabel = label.get(0L);
        System.out.println(confidence.get());
        System.out.println(predictedLabel);

        if (confidence.get() > CONFIDENCE_LEVEL) {
            return -1;
        }
        return predictedLabel;
    }
}
