package main.ui.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.database.StudentDB;
import main.face_recognition.OpencvFaceRecogniser;
import main.image_processing.PreprocessMatImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.model.Student;
import main.ui.utils.AlertDialogs;
import main.ui.utils.Navigation;
import main.utils.FileLocations;
import main.utils.Images;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import main.utils.Utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static main.utils.Images.getByteArrayFromFile;

public class RegisterNewStudentController implements Initializable {

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
    private JFXComboBox<String> sexCombo;

    @FXML
    private ImageView originalFrame;

    @FXML
    private Button cameraButton;

    @FXML
    ImageView cameraViewImageView;


    private Mat displayPicture;
    private Mat trainingImage;

    private ScheduledExecutorService timer;
    private VideoCapture capture;
    private boolean cameraActive;
    private static int cameraId = 0;
    private static CascadeClassifier faceCascade = new CascadeClassifier("resources\\haarcascades\\haarcascade_frontalface_alt.xml");

    private int absoluteFaceSize;

    protected void init() {
        this.capture = new VideoCapture();
        this.absoluteFaceSize = 0;
    }


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

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String otherName = otherNameTextField.getText();
        String sex = sexCombo.getSelectionModel().getSelectedItem();
        String matricNumber = matricNumberTextField.getText();

        if (validateEntry(firstName, lastName, matricNumber) && imageCaptured) {

            Imgcodecs.imwrite(FileLocations.TEMP_IMAGE_LOCATION, displayPicture);
            byte [] displayImage = getByteArrayFromFile(FileLocations.TEMP_IMAGE_LOCATION);

            if(displayImage == null){
                AlertDialogs.showErrorMessage("Error converting image to byte");

                return;
            }

            for(byte b : displayImage)
                System.out.print(b);

            Student student = new Student(0, firstName, lastName, otherName, matricNumber, sex,
                    displayImage);

            addStudentToList(student, displayPicture);

                ButtonType yes = new ButtonType("Yes");

                Optional<ButtonType> result = AlertDialogs.showConfirmDialog("Student added succesfully\n" +
                        "Would you like to add a new student?");

                if (result.get() == yes) {
                    Parent root = null;
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.REGISTER_NEW_STUDENT));
                        Scene registrationScene = new Scene(root);
                        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        primaryStage.setScene(registrationScene);
                        primaryStage.show();

                        final RegisterNewStudentController controller = (RegisterNewStudentController) loader.getController();
                        primaryStage.setOnCloseRequest(new javafx.event.EventHandler() {
                            @Override
                            public void handle(Event event) {
                                controller.setClosed();
                            }
                        });
                        root = (Parent) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.close();

                    setClosed();
                }

            } /*else {
                AlertDialogs.showErrorMessage("There was a problem adding the student to the database" +
                        "\nExit the student registration windows, reopen it and try again." +
                        "\nIf the problem persists, contact your administrator.");
            }

        }*/ else {
            AlertDialogs.showErrorMessage("Some fields are empty\nPlease ensure that: " +
                    "\n1.   All fields has been correctly and completely filled." +
                    "\n2.   You have captured an image of the student, then try again.");
        }

    }


    public void addStudentToList(Student student, Mat trainingData) {

        boolean success = false;

        StudentDB studentDb = new StudentDB();
        studentDb.addStudent(student);

            int id = studentDb.getLastId();

            if (id == -1)
                id = 1;

            int kernelSize = 3;
            Mat medianFilter = new Mat();

            Imgproc.medianBlur(trainingData, medianFilter, kernelSize);

            Mat sharpenedImage = new Mat(trainingData.rows(), trainingData.cols(), CvType.CV_8UC3);

            Imgproc.GaussianBlur(trainingData, sharpenedImage, new Size(0.0D, 0.0D), 3.0D);

            org.opencv.core.Core.addWeighted(trainingData, 1.5D, sharpenedImage, -0.5D, 0.0D, sharpenedImage);

            String imageName = FileLocations.TRAINING_DATA + id + "-" + student.getMatricNumber();

            Imgcodecs.imwrite(imageName + "1.pgm", trainingData);
            Imgcodecs.imwrite(imageName + "2.pgm", medianFilter);
            Imgcodecs.imwrite(imageName + "3.pgm", sharpenedImage);

            OpencvFaceRecogniser.trainRecognizer();


    }

    private boolean validateEntry(String firstName, String lastName, String matricNumber) {
        return (!firstName.isEmpty()) && (!lastName.isEmpty()) && (!matricNumber.isEmpty());
    }


    @FXML
    private Button startCameraBtn;

    @FXML
    private AnchorPane errorPane;

    boolean imageCaptured = false;

    @FXML
    private void inputEntered(ActionEvent ae) {
        this.startCameraBtn.setDisable(false);
    }

    @FXML
    public void returnToHome(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        setClosed();
    }


    @FXML
    protected void startCamera(ActionEvent event) {

        if (this.startCameraBtn.getText().equalsIgnoreCase("Start Camera")) {
            startCapture();
            this.startCameraBtn.setText("Capture");
        } else {
            this.imageCaptured = captureImage();
            if (this.imageCaptured) {
                this.startCameraBtn.setText("Start Camera");
                stopAcquisition();
                cameraActive = false;
            }
        }
    }


    public void startCapture() {
        if (!this.cameraActive) {
            this.capture.open(cameraId);
            if (this.capture.isOpened()) {
                this.cameraActive = true;

                Runnable frameGrabber = new Runnable() {
                    public void run() {
                        Mat frame = grabFrame(true);
                        Image imageToShow = Utils.mat2Image(frame);

                        updateImageView(cameraViewImageView, imageToShow);
                    }
                };
                this.timer = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0L, 33L, TimeUnit.MILLISECONDS);
            } else {
                System.err.println("Failed to open the camera connection...");
            }
        } else {
            this.cameraActive = false;
            stopAcquisition();
        }
    }


    private Mat grabFrame(boolean detect) {
        Mat frame = new Mat();
        if (this.capture.isOpened()) {
            try {
                this.capture.read(frame);
                if ((!frame.empty()) && (detect)) {
                    detectAndDisplay(frame);
                }
            } catch (Exception e) {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }
        return frame;
    }

    private void detectAndDisplay(Mat frame) {
        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        Imgproc.cvtColor(frame, grayFrame, 6);
        Imgproc.equalizeHist(grayFrame, grayFrame);

        if (this.absoluteFaceSize == 0) {
            int height = grayFrame.rows();
            if (Math.round(height * 0.2F) > 0) {
                this.absoluteFaceSize = Math.round(height * 0.2F);
            }
        }

        faceCascade.detectMultiScale(grayFrame, faces, 1.1D, 2, 2,
                new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

        if (faces.toArray().length > 1) {
            System.out.println("More than one face detected");
        }
        Rect[] facesArray = faces.toArray();
        for (int i = 0; i < facesArray.length; i++) {
            Imgproc.rectangle(frame, facesArray[i].tl(),
                    facesArray[i].br(), new org.opencv.core.Scalar(0.0D, 255.0D, 0.0D), 4);
        }
    }


    private boolean captureImage()
/*     */ {
/* 405 */
        Mat frame = grabFrame(false);
/* 406 */
        MatOfRect faces = new MatOfRect();
/* 407 */
        Mat grayFrame = new Mat();
/*     */
/*     */
/* 410 */
        Imgproc.cvtColor(frame, grayFrame, 6);
/*     */
/*     */
/* 413 */
        Imgproc.equalizeHist(grayFrame, grayFrame);
/*     */
/*     */
/* 416 */
        if (this.absoluteFaceSize == 0)
/*     */ {
/* 418 */
            int height = grayFrame.rows();
/* 419 */
            if (Math.round(height * 0.2F) > 0)
/*     */ {
/* 421 */
                this.absoluteFaceSize = Math.round(height * 0.2F);
/*     */
            }
/*     */
        }
/*     */
/*     */
/* 426 */
        faceCascade.detectMultiScale(grayFrame, faces, 1.1D, 2, 2, new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
/*     */
/*     */
/* 429 */
        if (faces.toArray().length != 1) {
/* 430 */
            System.out.println("More than one face detected or no face detected");
/*     */
/* 432 */
            return false;
/*     */
        }
/*     */
/*     */
/* 436 */
        Rect[] facesArray = faces.toArray();
/* 437 */
        for (int i = 0; i < facesArray.length; i++)
/*     */ {
/*     */
/* 440 */
            int x = 0;
            int y = 0;
            int height = 0;
            int width = 0;
/* 441 */
            Rect rect = facesArray[i];
/* 442 */
            Rect rectCrop = null;
/*     */
/* 444 */
            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
/*     */
/* 446 */
            Mat image_roi = new Mat(frame, rectCrop);
/*     */
/*     */
/* 449 */
            Imgproc.cvtColor(image_roi, grayFrame, 6);
/*     */
/*     */
/* 452 */
            rectCrop = new Rect(rect.x + 5, rect.y + 5, rect.width + 5, rect.height + 5);
/* 453 */
            Mat resizedImg = new Mat(frame, rectCrop);
/*     */
/*     */
/*     */
/* 457 */
            grayFrame = PreprocessMatImage.facePreproc(grayFrame, this.absoluteFaceSize);
/*     */
/* 459 */
            if (grayFrame == null) {
/* 460 */
                this.errorPane.setVisible(true);
/* 461 */
                return false;
/*     */
            }
            updateImageView(profilePictureImageView, Utils.mat2Image(frame));
/*     */
/* 464 */
            this.displayPicture = resizedImg;
/* 465 */
            this.trainingImage = grayFrame;
/* 466 */
            this.errorPane.setVisible(false);
/*     */
        }
/* 468 */
        return true;
/*     */
    }


    private void stopAcquisition() {
        if ((this.timer != null) && (!this.timer.isShutdown())) {
            try {
                this.timer.shutdown();
                this.timer.awaitTermination(33L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }
        if (this.capture.isOpened()) {
            this.capture.release();
            cameraActive = false;
        }
    }


    private void updateImageView(ImageView view, Image image)
/*     */ {
/* 503 */
        Utils.onFXThread(view.imageProperty(), image);
/*     */
    }

    /*     */
/*     */
/*     */
/*     */
/*     */
    protected void setClosed()
/*     */ {
/* 511 */
        stopAcquisition();
/*     */
    }

    /*     */
/*     */
    public void initialize(URL location, ResourceBundle resources)
/*     */ {
        ObservableList<String> gender = FXCollections.observableArrayList("Male", "Female");
        sexCombo.setItems(gender);
        sexCombo.getSelectionModel().selectFirst();

/* 516 */
        init();
/*     */
    }
}
