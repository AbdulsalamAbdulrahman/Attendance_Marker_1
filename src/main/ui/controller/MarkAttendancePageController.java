package main.ui.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.face_recognition.OpencvFaceRecogniser;
import main.image_processing.PreprocessMatImage;
import main.model.Lecturer;
import main.ui.utils.Navigation;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import main.utils.Utils;

import java.io.*;
import java.util.concurrent.ScheduledExecutorService;

public class MarkAttendancePageController implements Initializable{

    @FXML
    private ImageView cameraImageView;

    @FXML
    private Button startCameraButton;

    @FXML
    private ImageView displayPictureImageView;

    @FXML
    private JFXTextField firstNameTextField;

    @FXML
    private JFXTextField lastNameTextField;

    @FXML
    private JFXTextField otherNameTextField;

    @FXML
    private JFXTextField matricTextField;

    @FXML
    private JFXTextField sexTextField;

    @FXML
    private Label AttendanceMarkedLabel;

    @FXML
    private HBox matchFoundHbox;

    @FXML
    private Label matchNotFoundTextField;


    //=================GLOBAL VARIABLES
    private TextField thresholdTextField;
    private static int cameraId = 0;
    public static double threshold = 100.0D;
    private int absoluteFaceSize;
    private FileWriter writeFile;

    private BufferedReader br;
    private ScheduledExecutorService timer;
    private VideoCapture capture;
    private boolean cameraActive;
    private CascadeClassifier faceCascade = new CascadeClassifier("resources/haarcascades/haarcascade_frontalface_alt.xml");
    private String course;
    private Lecturer lecturer;

    public void setLecturerAndCourse(Lecturer lecturer, String course) {
        this.lecturer = lecturer;
        this.course = course;
    }

    protected void init(){
        this.capture = new VideoCapture();
        this.absoluteFaceSize = 0;
    }


    @FXML
    void closeWindow(ActionEvent event) {
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
        setClosed();
    }

    @FXML
    void nextStudent(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.MARK_ATTENDANCE_PAGE));
        javafx.scene.Parent root = null;
        try {
            root = (javafx.scene.Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene registrationScene = new Scene(root);
        Stage primaryStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(registrationScene);
        primaryStage.show();
        MarkAttendancePageController controller = loader.getController();

        controller.setLecturerAndCourse(this.lecturer, this.course);
        setClosed();

        primaryStage.setOnCloseRequest(new javafx.event.EventHandler() {

            @Override
            public void handle(Event event) {
                controller.setClosed();
            }

        });
    }

    @FXML
    void startCamera(ActionEvent event) {

        if(startCameraButton.getText().equals("Start Camera")) {
            startCapture();
            startCameraButton.setText("Stop Camera");
        }else{
            stopAcquisition();
            startCameraButton.setText("Start Camera");
        }
    }


    public void startCapture(){
        if (!this.cameraActive){
            this.capture.open(cameraId);
            if (this.capture.isOpened()) {
                this.cameraActive = true;
                Runnable frameGrabber = new Runnable(){
                    public void run(){
                        Mat frame = grabFrame(true);
                        Image imageToShow = Utils.mat2Image(frame);
                        updateImageView(cameraImageView, imageToShow);
                        recogniseFace(frame);
                    }
                };
                this.timer = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0L, 33L, java.util.concurrent.TimeUnit.MILLISECONDS);
            }else{
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

        this.faceCascade.detectMultiScale(grayFrame, faces, 1.1D, 2, 2, new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

        if (faces.toArray().length > 1) {

            System.out.println("More than one face detected");

        }
        Rect[] facesArray = faces.toArray();

        for (int i = 0; i < facesArray.length; i++) {
            Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new org.opencv.core.Scalar(0.0D, 255.0D, 0.0D), 1);

        }
    }

    private void getStudentData(int label) {
       /* StudentDb studentDb = new StudentDb(this.course, false);
        Student1 student = studentDb.getStudent(label);

        if (student != null) {
            updateLabels(firstNameTextField, student.getFirstName());
            updateLabels(lastNameTextField, student.getLastName());
            updateLabels(otherNameTextField, student.getOtherName());
            updateLabels(sexTextField, student.getSex());
            updateLabels(matricTextField, student.getMatricNumber());
            java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            java.util.Date date = new java.util.Date();
            String currentDate = dateFormat.format(date);
            updateLabels(AttendanceMarkedLabel, "Attendance Marked for " + currentDate);
            AttendanceMarkedLabel.setVisible(true);
            student.incrementNoOfDaysPresent();
            studentDb.markAttendance(label);
        }*/
    }


    private void recogniseFace(Mat frame) {
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
        this.faceCascade.detectMultiScale(grayFrame, faces, 1.1D, 2, 2, new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

        if (faces.toArray().length == 1) {
            Rect[] facesArray = faces.toArray();
            for (int i = 0; i < facesArray.length; i++) {

                int x = 0;
                int y = 0;
                int height = 0;
                int width = 0;

                Rect rect = facesArray[i];

                Rect rectCrop = null;

                Imgproc.rectangle(frame, new org.opencv.core.Point(rect.x, rect.y), new org.opencv.core.Point(rect.x + rect.width, rect.y + rect.height), new org.opencv.core.Scalar(0.0D, 255.0D, 0.0D));

                rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);

                Mat image_roi = new Mat(frame, rectCrop);

                Imgproc.cvtColor(image_roi, grayFrame, 6);

                grayFrame = PreprocessMatImage.facePreproc(grayFrame, this.absoluteFaceSize);
                if (grayFrame != null) {
                    org.opencv.imgcodecs.Imgcodecs.imwrite("resources\\temp.pgm", grayFrame);
                    int label = OpencvFaceRecogniser.predictedLabel("resources\\temp.pgm");
/*
                    if ((label > 0) && (label <= main.model.Students.getInstance().getSize())) {
                        stopAcquisition();

                        getStudentData(label);

                        updateLabels(this.studentFoundLabel, "MATCH FOUND");
                        updateBoolean(this.studentFoundLabel1, Boolean.valueOf(false));*/
                    } /*else {
                        updateLabels(this.studentFoundLabel, " ");
                        updateBoolean(this.studentFoundLabel1, Boolean.valueOf(true));
                    }
                }*/ else {
                    /*updateLabels(this.studentFoundLabel, " ");
                    updateBoolean(this.studentFoundLabel1, Boolean.valueOf(true));*/
                }
            }
        }
    }

    private void stopAcquisition() {
        if ((this.timer != null) && (!this.timer.isShutdown())) {
            try {
                this.timer.shutdown();
                this.timer.awaitTermination(33L, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }
        if (this.capture.isOpened()) {
            this.capture.release();
        }
    }

    private void updateImageView(ImageView view, Image image) {
        Utils.onFXThread(view.imageProperty(), image);
    }

/*    private void updateLabels(Label label, String text) {
        Utils.onFXThread(label.textProperty(), text);
    }

    private void updateBoolean(Label label, Boolean b) {
        System.out.println("got here");
        Utils.onFXThread(label.visibleProperty(), b);
    }*/

    protected void setClosed() {
        stopAcquisition();
    }

    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        init();
        //startCapture();
    }
}
