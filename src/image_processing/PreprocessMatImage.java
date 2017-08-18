/*     */ package image_processing;
/*     */ 
/*     */ import org.opencv.core.CvType;
/*     */ import org.opencv.core.Mat;
/*     */ import org.opencv.core.MatOfRect;
/*     */ import org.opencv.core.Point;
/*     */ import org.opencv.core.Rect;
/*     */ import org.opencv.core.Scalar;
/*     */ import org.opencv.core.Size;
/*     */ import org.opencv.imgproc.Imgproc;
/*     */ import org.opencv.objdetect.CascadeClassifier;
/*     */ 
/*     */ 
/*     */ public class PreprocessMatImage
/*     */ {
/*  16 */   private static CascadeClassifier leftEyeCascade = new CascadeClassifier("resources\\haarcascades\\haarcascade_mcs_lefteye.xml");
/*  17 */   private static CascadeClassifier rightEyeCascade = new CascadeClassifier("resources\\haarcascades\\haarcascade_mcs_righteye.xml");
/*  18 */   private static CascadeClassifier backupEyeCascade = new CascadeClassifier("resources\\haarcascades\\haarcascade_eye.xml");
/*     */   
/*     */ 
/*     */ 
/*     */   private static final int WIDTH = 80;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final int HEIGHT = 80;
/*     */   
/*     */ 
/*     */ 
/*     */   public static Mat facePreproc(Mat m, int mAbsoluteFaceSize)
/*     */   {
/*  32 */     double EYE_SX = 0.1D;
/*  33 */     double EYE_SY = 0.19D;
/*  34 */     double EYE_SW = 0.4D;
/*  35 */     double EYE_SH = 0.36D;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  40 */     int leftX = (int)Math.round(m.cols() * EYE_SX);
/*  41 */     int topY = (int)Math.round(m.rows() * EYE_SY);
/*  42 */     int widthX = (int)Math.round(m.cols() * EYE_SW);
/*  43 */     int heightY = (int)Math.round(m.rows() * EYE_SH);
/*  44 */     int rightX = (int)Math.round(m.cols() * (1.0D - EYE_SX - EYE_SW));
/*     */     
/*  46 */     Mat topLeftOfFace = m.submat(new Rect(leftX, topY, widthX, heightY));
/*  47 */     Mat topRightOfFace = m.submat(new Rect(rightX, topY, widthX, heightY));
/*     */     
/*     */ 
/*  50 */     int mAbsoluteEyeSize = (int)Math.round(mAbsoluteFaceSize * 0.1D);
/*  51 */     MatOfRect leftEyeRect = new MatOfRect();
/*  52 */     MatOfRect rightEyeRect = new MatOfRect();
/*     */     
/*  54 */     int flags = 12;
/*     */     
/*     */ 
/*  57 */     leftEyeCascade.detectMultiScale(topLeftOfFace, leftEyeRect, 1.1D, 2, flags, new Size(mAbsoluteEyeSize, mAbsoluteEyeSize), new Size());
/*  58 */     rightEyeCascade.detectMultiScale(topRightOfFace, rightEyeRect, 1.1D, 2, flags, new Size(mAbsoluteEyeSize, mAbsoluteEyeSize), new Size());
/*     */     
/*     */ 
/*  61 */     if (leftEyeRect.toArray().length == 0)
/*  62 */       backupEyeCascade.detectMultiScale(topLeftOfFace, leftEyeRect, 1.1D, 2, flags, new Size(mAbsoluteEyeSize, mAbsoluteEyeSize), new Size());
/*  63 */     if (rightEyeRect.toArray().length == 0) {
/*  64 */       backupEyeCascade.detectMultiScale(topRightOfFace, rightEyeRect, 1.1D, 2, flags, new Size(mAbsoluteEyeSize, mAbsoluteEyeSize), new Size());
/*     */     }
/*     */     
/*  67 */     Rect[] LeftEye = leftEyeRect.toArray();
/*  68 */     Rect[] RightEye = rightEyeRect.toArray();
/*     */     
/*  70 */     Point leftEyeCenter = new Point(-1.0D, -1.0D);
/*  71 */     Point rightEyeCenter = new Point(-1.0D, -1.0D);
/*     */     
/*  73 */     if (LeftEye.length > 0) {
/*  74 */       leftEyeCenter.x = (LeftEye[0].x + LeftEye[0].width / 2 + leftX);
/*  75 */       leftEyeCenter.y = (LeftEye[0].y + LeftEye[0].height / 2 + topY);
/*     */     }
/*     */     
/*     */ 
/*  79 */     if (RightEye.length > 0) {
/*  80 */       rightEyeCenter.x = (RightEye[0].x + RightEye[0].width / 2 + rightX);
/*  81 */       rightEyeCenter.y = (RightEye[0].y + RightEye[0].height / 2 + topY);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  86 */     boolean botheyesfound = false;
/*  87 */     if ((leftEyeCenter.x >= 0.0D) && (rightEyeCenter.x >= 0.0D)) {
/*  88 */       botheyesfound = true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  96 */     if (botheyesfound)
/*     */     {
/*     */ 
/*  99 */       Point eyesCenter = new Point();
/* 100 */       eyesCenter.x = ((leftEyeCenter.x + rightEyeCenter.x) * 0.5D);
/* 101 */       eyesCenter.y = ((leftEyeCenter.y + rightEyeCenter.y) * 0.5D);
/*     */       
/*     */ 
/* 104 */       double dy = rightEyeCenter.y - leftEyeCenter.y;
/* 105 */       double dx = rightEyeCenter.x - leftEyeCenter.x;
/* 106 */       double len = Math.sqrt(dx * dx + dy * dy);
/*     */       
/*     */ 
/* 109 */       double angle = Math.atan2(dy, dx) * 180.0D / 3.141592653589793D;
/*     */       
/*     */ 
/*     */ 
/* 113 */       double DESIRED_LEFT_EYE_X = 0.16D;
/* 114 */       double DESIRED_LEFT_EYE_Y = 0.14D;
/* 115 */       double DESIRED_RIGHT_EYE_X = 0.84D;
/*     */       
/*     */ 
/*     */ 
/* 119 */       int DESIRED_FACE_WIDTH = 80;
/* 120 */       int DESIRED_FACE_HEIGHT = 80;
/* 121 */       double desiredLen = DESIRED_RIGHT_EYE_X - 0.16D;
/* 122 */       double scale = desiredLen * DESIRED_FACE_WIDTH / len;
/*     */       
/*     */ 
/* 125 */       Mat rot_mat = Imgproc.getRotationMatrix2D(eyesCenter, angle, scale);
/*     */       
/*     */ 
/* 128 */       double ex = DESIRED_FACE_WIDTH * 0.5F - eyesCenter.x;
/* 129 */       double ey = DESIRED_FACE_HEIGHT * DESIRED_LEFT_EYE_Y - eyesCenter.y;
/* 130 */       rot_mat.put(0, 2, new double[] { rot_mat.get(0, 2)[0] + ex });
/* 131 */       rot_mat.put(1, 2, new double[] { rot_mat.get(1, 2)[0] + ey });
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 136 */       Mat warped = new Mat(DESIRED_FACE_HEIGHT, DESIRED_FACE_WIDTH, 0, new Scalar(128.0D));
/* 137 */       Imgproc.warpAffine(m, warped, rot_mat, warped.size());
/* 138 */       m = warped;
/*     */       
/*     */ 
/* 141 */       int w = m.cols();
/* 142 */       int h = m.rows();
/* 143 */       Mat wholeFace = new Mat();
/* 144 */       Imgproc.equalizeHist(m, wholeFace);
/* 145 */       int midX = w / 2;
/* 146 */       Mat leftSide = m.submat(new Rect(0, 0, midX, h));
/* 147 */       Mat rightSide = m.submat(new Rect(midX, 0, w - midX, h));
/* 148 */       Imgproc.equalizeHist(leftSide, leftSide);
/* 149 */       Imgproc.equalizeHist(rightSide, rightSide);
/*     */       
/* 151 */       for (int y = 0; y < h; y++) {
/* 152 */         for (int x = 0; x < w; x++) {
/* 153 */           if (x >= w / 4)
/*     */           {
/*     */ 
/* 156 */             if (x < w * 2 / 4)
/*     */             {
/* 158 */               int lv = (int)leftSide.get(y, x)[0];
/* 159 */               int wv = (int)wholeFace.get(y, x)[0];
/*     */               
/*     */ 
/* 162 */               float f = (x - w * 1 / 4) / (w / 4);
/* 163 */               m.put(y, x, new double[] { Math.round((1.0F - f) * lv + f * wv) });
/* 164 */             } else if (x < w * 3 / 4)
/*     */             {
/* 166 */               int rv = (int)rightSide.get(y, x - midX)[0];
/* 167 */               int wv = (int)wholeFace.get(y, x)[0];
/*     */               
/*     */ 
/* 170 */               float f = (x - w * 2 / 4) / (w / 4);
/* 171 */               m.put(y, x, new double[] { Math.round((1.0F - f) * wv + f * rv) });
/*     */             }
/*     */             else {
/* 174 */               m.put(y, x, rightSide.get(y, x - midX));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 180 */       Mat filtered = new Mat(m.size(), 0);
/* 181 */       Imgproc.bilateralFilter(m, filtered, 0, 20.0D, 2.0D);
/*     */       
/*     */ 
/*     */ 
/* 185 */       Mat mask = new Mat(m.size(), CvType.CV_8UC1, new Scalar(255.0D));
/* 186 */       double dw = DESIRED_FACE_WIDTH;
/* 187 */       double dh = DESIRED_FACE_HEIGHT;
/* 188 */       Point faceCenter = new Point(Math.round(dw * 0.5D), Math.round(dh * 0.4D));
/* 189 */       Size size = new Size(Math.round(dw * 0.5D), Math.round(dh * 0.8D));
/* 190 */       Imgproc.ellipse(mask, faceCenter, size, 0.0D, 0.0D, 360.0D, new Scalar(0.0D), -1);
/*     */       
/*     */ 
/*     */ 
/* 194 */       filtered.setTo(new Scalar(128.0D), mask);
/*     */       
/*     */ 
/* 197 */       return m;
/*     */     }
/* 199 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Oga Emma\Downloads\Compressed\AttendanceMarker_v2.01_jar_2\AttendanceMarker_jar\AttendanceMarker.jar!\main\preprocessimage\PreprocessMatImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */