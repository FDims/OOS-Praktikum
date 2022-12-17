package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent fxmlLoader = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Main-view.fxml")));
            Scene scene = new Scene(fxmlLoader);
            stage.setTitle("Praktikum5");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        launch(args);
    }

}

