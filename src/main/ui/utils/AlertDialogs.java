package main.ui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

public class AlertDialogs {

    public static Optional<ButtonType> showConfirmDialog(String body) {

        Alert confirmation = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(null);
        confirmation.setContentText(body);

        confirmation.initModality(Modality.APPLICATION_MODAL);

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");

        confirmation.getButtonTypes().

                setAll(new ButtonType[]{yes, no});

        Optional<ButtonType> result = confirmation.showAndWait();
        return result;
    }

    public static void showErrorMessage(String msg){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText(null);
        error.setContentText(msg);

        error.initModality(Modality.APPLICATION_MODAL);

        error.showAndWait();
    }

    public static void showInformationDialog(String msg){
        Alert error = new Alert(Alert.AlertType.INFORMATION);
        error.setHeaderText(null);
        error.setContentText(msg);

        error.initModality(Modality.APPLICATION_MODAL);

        error.showAndWait();
    }
}
