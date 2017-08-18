import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.opencv.core.Core;
import ui.controller.RegisterCoursePageController;
import ui.controller.RegisterNewStudentController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/fxml/register_new_student.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("ui/fxml/user_home_page.fxml"));

        AnchorPane root = loader.load();

        primaryStage.setScene(new Scene(root, 1066, 700));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        RegisterNewStudentController controller = loader.getController();

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


    }
}
