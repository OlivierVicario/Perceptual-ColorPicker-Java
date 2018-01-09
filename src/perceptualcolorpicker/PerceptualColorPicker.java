/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptualcolorpicker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author olivier
 */
public class PerceptualColorPicker extends Application {
    private String darkStyleUrl = getClass().getResource("darkstyle.css").toExternalForm();
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Doc.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(darkStyleUrl);
        stage.setScene(scene);
        stage.setTitle("Perceptual color picker 1.0.0");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
