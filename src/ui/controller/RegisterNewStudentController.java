package ui.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.tk.Toolkit;
import image_processing.PreprocessMatImage;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;
import utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RegisterNewStudentController implements Initializable{

    @FXML
    private ImageView profilePictureImageView;

    @FXML
    private JFXTextField firstNameTextField;

    @FXML
    private JFXTextField lastNameTextField;

    @FXML
    private JFXTextField otherNameTextField;

    @FXML
    private JFXTextField matricNumberTextField;

    @FXML
    private JFXComboBox<?> sexCombo;

    @FXML
    private ImageView originalFrame;

    @FXML
    private Button cameraButton;

    ////////////////////////////////////////////////////
    private Mat trainingImage = null;

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        setClosed();
    }

    @FXML
    void maximizeWindow(ActionEvent event) {

    }

    @FXML
    void minimizeWindow(ActionEvent event) {

    }

    @FXML
    void saveStudent(ActionEvent event) {

    }



    public byte[] getImageByte(Mat matImage){
        Imgcodecs.imwrite("temp.png", matImage);
        return utils.Images.getByteArrayFromFile("temp.png");
    }

    @FXML
/*     */   private TextField firstNameTF;
    /*     */   @FXML
/*     */   private TextField lastNameTF;
    /*     */   @FXML
/*     */   private TextField otherNameTF;
    /*     */   @FXML
/*     */   private TextField matricNumberTF;
    /*     */   @FXML
/*     */   private ToggleGroup sexGroup;
    /*     */   @FXML
/*     */   ImageView cameraViewImageView;
    /*     */   @FXML
/*     */   private Button startCameraBtn;
    /*     */   @FXML
/*     */   private AnchorPane errorPane;
    /*  63 */   boolean imageCaptured = false;
    /*     */   private Mat displayPicture;
    /*     */   private Mat trainingData;
    /*     */
/*     */   @FXML
/*     */   private void inputEntered(ActionEvent ae)
/*     */   {
/*  70 */     this.startCameraBtn.setDisable(false);
/*     */   }
    /*     */
    @FXML
    public void returnToHome(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        setClosed();
    }
    /*     */
/*     */
/*     */  /* @FXML
        public void RegisterStudent(ActionEvent ae)
          throws IOException
        {
  86      String firstName = this.firstNameTF.getText().trim();
  87      String lastName = this.lastNameTF.getText().trim();
  88      String otherName = this.otherNameTF.getText().trim();
  89      String matricNumber = this.matricNumberTF.getText().trim();

  91      RadioButton sexRadio = (RadioButton)this.sexGroup.getSelectedToggle();
  92      String sex = sexRadio.getText();


  95      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");






 102      if ((validateEntry(firstName, lastName, otherName, matricNumber, sex)) && (this.imageCaptured))
          {


 118        Student1 student = new Student1(0, firstName, lastName, otherName, sex, matricNumber, 0);


 121        addStudentToList(student, this.displayPicture, this.trainingData);

 123        Alert confirmation = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
 124        confirmation.setHeaderText("Student added successfully...");
 125        confirmation.setContentText("Would you like to add a new student?");

 127        ButtonType yes = new ButtonType("Yes");
 128        ButtonType no = new ButtonType("No");

 130        confirmation.getButtonTypes().setAll(new ButtonType[] { yes, no });

 132        Optional<ButtonType> result = confirmation.showAndWait();

 134        if (result.get() == yes) {
 135          FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/view/fxml/Registration.fxml"));


 138          Parent root = null;
              try {
 140            root = (Parent)loader.load();
              } catch (IOException e) {
 142            e.printStackTrace();
              }


 146          Scene registrationScene = new Scene(root);
 147          Stage primaryStage = AttendanceMarker.window;

 149          primaryStage.setScene(registrationScene);
 150          primaryStage.show();

 152          final RegistrationStudentController controller = (RegistrationStudentController)loader.getController();
 153          primaryStage.setOnCloseRequest(new javafx.event.EventHandler()
              {
                                    public void handle(WindowEvent we) {
 156              controller.setClosed();
                }
              });
            }
            else
            {
 162          Parent homePage = (Parent)FXMLLoader.load(getClass().getResource("/main/view/fxml/AdminPage.fxml"));
 163          Scene registrationScene = new Scene(homePage);
 164          Stage primaryStage = AttendanceMarker.window;

 166          primaryStage.setScene(registrationScene);
 167          primaryStage.show();

 169          setClosed();
            }

          }
          else
          {
 175        System.out.println("Please fill all fields correctly");
          }
        }
         */
/*     */
/*     */
/*     *//*   public void addStudentToList(Student1 student, Mat displayPicture, Mat trainingData)
*//*     *//*   {
*//* 183 *//*     StudentDb studentDb = new StudentDb("MasterStudentTable", false);
*//* 184 *//*     studentDb.insertStudent(student);
*//*     *//*
*//*     *//*
*//*     *//*
*//* 188 *//*     int id = studentDb.getNoOfEntries();
*//*     *//*
*//*     *//*
*//* 191 *//*     int kernelSize = 3;
*//* 192 *//*     Mat medianFilter = new Mat();
*//* 193 *//*     Imgproc.medianBlur(trainingData, medianFilter, kernelSize);
*//*     *//*
*//*     *//*
*//* 196 *//*     Mat sharpenedImage = new Mat(trainingData.rows(), trainingData.cols(), CvType.CV_8UC3);
*//*     *//*
*//* 198 *//*     Imgproc.GaussianBlur(trainingData, sharpenedImage, new Size(0.0D, 0.0D), 3.0D);
*//* 199 *//*     org.opencv.core.Core.addWeighted(trainingData, 1.5D, sharpenedImage, -0.5D, 0.0D, sharpenedImage);
*//*     *//*
*//*     *//*
*//* 202 *//*     String imageName = id + "-" + student.getMatricNumber();
*//* 203 *//*     Imgcodecs.imwrite("resources/images/full_img/" + imageName + ".png", displayPicture);
*//* 204 *//*     Imgcodecs.imwrite("resources/images/training_data/" + imageName + "1.pgm", trainingData);
*//* 205 *//*     Imgcodecs.imwrite("resources/images/training_data/" + imageName + "2.pgm", medianFilter);
*//* 206 *//*     Imgcodecs.imwrite("resources/images/training_data/" + imageName + "3.pgm", sharpenedImage);
*//*     *//*
*//* 208 *//*     main.preprocessimage.OpencvFaceRecogniser.trainRecognizer();
*//*     *//*   }*/
    /*     */
/*     */
/*     */
/*     */
/*     */   private boolean validateEntry(String firstName, String lastName, String otherName, String matricNumber, String sex)
/*     */   {
/* 216 */     return (!firstName.isEmpty()) && (!lastName.isEmpty()) && (!matricNumber.isEmpty()) && (!sex.isEmpty());
/*     */   }
    /*     */
/*     */
/*     */
/*     */   @FXML
/*     */   protected void startCamera(ActionEvent event)
/*     */   {
/* 224 */     if (true /*validateEntry(this.firstNameTF.getText().trim(), this.lastNameTF.getText().trim(), this.otherNameTF.getText().trim(), this.matricNumberTF
*//* 225 *//*       .getText().trim(), "male")*/) {
/* 226 */       if (this.startCameraBtn.getText().equalsIgnoreCase("Start Camera")) {
/* 227 */         startCapture();
/* 228 */         this.startCameraBtn.setText("Capture");
/*     */       } else {
/* 230 */         this.imageCaptured = captureImage();
/*     */
/* 232 */         if (this.imageCaptured) {
/* 233 */           this.startCameraBtn.setText("Start Camera");
/* 234 */           stopAcquisition();
/*     */         }
/*     */       }
/*     */     } else {
/* 238 */       System.out.println("Please check that all fields has been correctly entered");
/*     */     }
/*     */   }
    /*     */
/*     */
/*     */
/*     */
/*     */   private ScheduledExecutorService timer;
    /*     */
/*     */
/*     */   private VideoCapture capture;
    /*     */
/*     */
/*     */   private boolean cameraActive;
    /*     */
/*     */
/* 254 */   private static int cameraId = 0;
    /*     */
/*     */
/* 257 */   private static CascadeClassifier faceCascade = new CascadeClassifier("resources\\haarcascades\\haarcascade_frontalface_alt.xml");
    /*     */
/*     */
/*     */   private int absoluteFaceSize;
    /*     */
/*     */
/*     */   protected void init()
/*     */   {
/* 265 */     this.capture = new VideoCapture();
/*     */
/* 267 */     this.absoluteFaceSize = 0;
/*     */   }
    /*     */
/*     */   public void startCapture()
/*     */   {
/* 279 */     if (!this.cameraActive)
/*     */     {
/*     */
/* 282 */       this.capture.open(cameraId);
/*     */
/*     */
/* 285 */       if (this.capture.isOpened()) {
/* 286 */         this.cameraActive = true;
/*     */
/*     */
/* 289 */         Runnable frameGrabber = new Runnable()
/*     */         {
                    /*     */
/*     */           public void run()
/*     */           {
/* 294 */             Mat frame = grabFrame(true);
/*     */
/* 296 */             Image imageToShow = Utils.mat2Image(frame);
/* 297 */             updateImageView(cameraViewImageView, imageToShow);
/*     */           }
/*     */
/* 300 */         };
/* 301 */         this.timer = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
/* 302 */         this.timer.scheduleAtFixedRate(frameGrabber, 0L, 33L, TimeUnit.MILLISECONDS);
/*     */       }
/*     */       else
/*     */       {
/* 306 */         System.err.println("Failed to open the camera connection...");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 311 */       this.cameraActive = false;
/*     */
/*     */
/* 314 */       stopAcquisition();
/*     */     }
/*     */   }
    /*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   private Mat grabFrame(boolean detect)
/*     */   {
/* 325 */     Mat frame = new Mat();
/*     */
/*     */
/* 328 */     if (this.capture.isOpened())
/*     */     {
/*     */       try
/*     */       {
/*     */
/* 333 */         this.capture.read(frame);
/*     */
/*     */
/* 336 */         if ((!frame.empty()) && (detect))
/*     */         {
/*     */
/* 339 */           detectAndDisplay(frame);
/*     */         }
/*     */
/*     */
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 346 */         System.err.println("Exception during the image elaboration: " + e);
/*     */       }
/*     */     }
/*     */
/* 350 */     return frame;
/*     */   }
    /*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   private void detectAndDisplay(Mat frame)
/*     */   {
/* 361 */     MatOfRect faces = new MatOfRect();
/* 362 */     Mat grayFrame = new Mat();
/*     */
/*     */
/* 365 */     Imgproc.cvtColor(frame, grayFrame, 6);
/*     */
/*     */
/* 368 */     Imgproc.equalizeHist(grayFrame, grayFrame);
/*     */
/*     */
/* 371 */     if (this.absoluteFaceSize == 0)
/*     */     {
/* 373 */       int height = grayFrame.rows();
/* 374 */       if (Math.round(height * 0.2F) > 0)
/*     */       {
/* 376 */         this.absoluteFaceSize = Math.round(height * 0.2F);
/*     */       }
/*     */     }
/*     */
/* 380 */     FilenameFilter xmlFilter = new FilenameFilter() {
            /*     */       public boolean accept(File dir, String name) {
/* 382 */         name = name.toLowerCase();
/* 383 */         return name.endsWith(".xml");
/*     */       }
/*     */
/*     */
/* 387 */     };
/* 388 */     faceCascade.detectMultiScale(grayFrame, faces, 1.1D, 2, 2, new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
/*     */
/*     */
/* 391 */     if (faces.toArray().length > 1) {
/* 392 */       System.out.println("More than one face detected");
/*     */     }
/*     */
/*     */
/* 396 */     Rect[] facesArray = faces.toArray();
/* 397 */     for (int i = 0; i < facesArray.length; i++)
/*     */     {
/* 399 */       Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new org.opencv.core.Scalar(0.0D, 255.0D, 0.0D), 4);
/*     */     }
/*     */   }
    /*     */
/*     */   private boolean captureImage()
/*     */   {
/* 405 */     Mat frame = grabFrame(false);
/* 406 */     MatOfRect faces = new MatOfRect();
/* 407 */     Mat grayFrame = new Mat();
/*     */
/*     */
/* 410 */     Imgproc.cvtColor(frame, grayFrame, 6);
/*     */
/*     */
/* 413 */     Imgproc.equalizeHist(grayFrame, grayFrame);
/*     */
/*     */
/* 416 */     if (this.absoluteFaceSize == 0)
/*     */     {
/* 418 */       int height = grayFrame.rows();
/* 419 */       if (Math.round(height * 0.2F) > 0)
/*     */       {
/* 421 */         this.absoluteFaceSize = Math.round(height * 0.2F);
/*     */       }
/*     */     }
/*     */
/*     */
/* 426 */     faceCascade.detectMultiScale(grayFrame, faces, 1.1D, 2, 2, new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
/*     */
/*     */
/* 429 */     if (faces.toArray().length != 1) {
/* 430 */       System.out.println("More than one face detected or no face detected");
/*     */
/* 432 */       return false;
/*     */     }
/*     */
/*     */
/* 436 */     Rect[] facesArray = faces.toArray();
/* 437 */     for (int i = 0; i < facesArray.length; i++)
/*     */     {
/*     */
/* 440 */       int x = 0;int y = 0;int height = 0;int width = 0;
/* 441 */       Rect rect = facesArray[i];
/* 442 */       Rect rectCrop = null;
/*     */
/* 444 */       rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
/*     */
/* 446 */       Mat image_roi = new Mat(frame, rectCrop);
/*     */
/*     */
/* 449 */       Imgproc.cvtColor(image_roi, grayFrame, 6);
/*     */
/*     */
/* 452 */       rectCrop = new Rect(rect.x + 5, rect.y + 5, rect.width + 5, rect.height + 5);
/* 453 */       Mat resizedImg = new Mat(frame, rectCrop);
/*     */
/*     */
/*     */
/* 457 */       grayFrame = PreprocessMatImage.facePreproc(grayFrame, this.absoluteFaceSize);
/*     */
/* 459 */       if (grayFrame == null) {
/* 460 */         this.errorPane.setVisible(true);
/* 461 */         return false;
/*     */       }updateImageView(profilePictureImageView, Utils.mat2Image(frame));
/*     */
/* 464 */       this.displayPicture = resizedImg;
/* 465 */       this.trainingData = grayFrame;
/* 466 */       this.errorPane.setVisible(false);
/*     */     }
/* 468 */     return true;
/*     */   }
    /*     */
/*     */
/*     */   private void stopAcquisition()
/*     */   {
/* 479 */     if ((this.timer != null) && (!this.timer.isShutdown()))
/*     */     {
/*     */       try
/*     */       {
/*     */
/* 484 */         this.timer.shutdown();
/* 485 */         this.timer.awaitTermination(33L, TimeUnit.MILLISECONDS);
/*     */
/*     */       }
/*     */       catch (InterruptedException e)
/*     */       {
/* 490 */         System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
/*     */       }
/*     */     }
/*     */
/* 494 */     if (this.capture.isOpened())
/*     */     {
/*     */
/* 497 */       this.capture.release();
/*     */     }
/*     */   }
    /*     */
/*     */   private void updateImageView(ImageView view, Image image)
/*     */   {
/* 503 */     Utils.onFXThread(view.imageProperty(), image);
/*     */   }
    /*     */
/*     */
/*     */
/*     */
/*     */   protected void setClosed()
/*     */   {
/* 511 */     stopAcquisition();
/*     */   }
    /*     */
/*     */   public void initialize(URL location, ResourceBundle resources)
/*     */   {
/* 516 */     init();
/*     */   }
}
