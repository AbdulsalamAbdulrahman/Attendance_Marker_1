package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.face_recognition.OpencvFaceRecogniser;
import main.ui.utils.Navigation;
import org.opencv.core.Core;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigation.LOGIN_PAGE));

        Parent root = loader.load();

        /*JFXDecorator decorator = new JFXDecorator(primaryStage, root, false, false, true);
        decorator.setCustomMaximize(true);
        decorator.setMaximized(true);
        decorator.setBorder(Border.EMPTY);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(decorator));*/

        primaryStage.setScene(new Scene(root));

        //RegisterNewStudentController controller = loader.getController();

        primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we)
            {
                //controller.setClosed();
            }
        }));

        primaryStage.show();
    }

    

    public static void main(String[] args) {

        // load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        launch(args);

        OpencvFaceRecogniser.trainRecognizer();


    }
}
