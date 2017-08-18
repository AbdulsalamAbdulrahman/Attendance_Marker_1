/*     */ package face_recognition;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.IntBuffer;
/*     */ import org.bytedeco.javacpp.DoublePointer;
/*     */ import org.bytedeco.javacpp.IntPointer;
/*     */ import org.bytedeco.javacpp.opencv_core;
/*     */ import org.bytedeco.javacpp.opencv_core.Mat;
/*     */ import org.bytedeco.javacpp.opencv_core.MatVector;
/*     */ import org.bytedeco.javacpp.opencv_face;
/*     */ import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
/*     */ import org.bytedeco.javacpp.opencv_imgcodecs;
/*     */ 
/*     */ 
/*     */ public class OpencvFaceRecogniser
/*     */ {
/*     */   public static FaceRecognizer faceRecognizer;
/*     */   private static final String trainingDirPath = "resources\\images\\training_data";
/*     */   
/*     */   public static void trainRecognizer()
/*     */   {
/*  47 */     String trainingDir = "resources\\images\\training_data";
/*     */     
/*  49 */     File root = new File(trainingDir);
/*     */     
/*  51 */     FilenameFilter imgFilter = new FilenameFilter() {
/*     */       public boolean accept(File dir, String name) {
/*  53 */         name = name.toLowerCase();
/*  54 */         return (name.endsWith(".jpg")) || (name.endsWith(".pgm")) || (name.endsWith(".png"));
/*     */       }
/*     */       
/*  57 */     };
/*  58 */     File[] imageFiles = root.listFiles(imgFilter);
/*     */     
/*  60 */     if (imageFiles.length > 0)
/*     */     {
/*  62 */       MatVector images = new MatVector(imageFiles.length);
/*     */       
/*  64 */       Mat labels = new Mat(imageFiles.length, 1, opencv_core.CV_32SC1);
/*  65 */       IntBuffer labelsBuf = (IntBuffer)labels.createBuffer();
/*     */       
/*  67 */       int counter = 0;
/*     */       
/*  69 */       for (File image : imageFiles) {
/*  70 */         Mat img = opencv_imgcodecs.imread(image.getAbsolutePath(), 0);
/*     */         
/*  72 */         int label = Integer.parseInt(image.getName().split("\\-")[0]);
/*     */         
/*  74 */         images.put(counter, img);
/*     */         
/*  76 */         labelsBuf.put(counter, label);
/*     */         
/*  78 */         counter++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  83 */       faceRecognizer = opencv_face.createLBPHFaceRecognizer();
/*     */       
/*  85 */       faceRecognizer.train(images, labels);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int predictedLabel(String testImagePath)
/*     */   {
/*  91 */     Mat testImage = opencv_imgcodecs.imread(testImagePath, 0);
/*     */     
/*  93 */     IntPointer label = new IntPointer(1L);
/*  94 */     DoublePointer confidence = new DoublePointer(1L);
/*  95 */     faceRecognizer.predict(testImage, label, confidence);
/*  96 */     int predictedLabel = label.get(0L);
/*     */     
/*  98 */     System.out.println(confidence.get());
/*  99 */     System.out.println(predictedLabel);
/*     */     
/* 101 */     /*if (confidence.get() > MarkAttendanceController.threshold) {
*//* 102 *//*       return -1;
*//*     *//*     }*/

    if (confidence.get() > 200) {
        //todo make this value dynamic
/* 102 */       return -1;
/*     */     }
/* 104 */     return predictedLabel;
/*     */   }
/*     */ }
