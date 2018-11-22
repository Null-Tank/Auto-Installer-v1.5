import com.dropbox.core.DbxException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.loginController;
import install.installController;
import home.homeController;
import javafx.scene.image.Image;
import server.Data;

import java.io.File;
import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Gets the OS name
        System.out.println(System.getProperty("os.name"));

        // Sets Login Stage

        // IDE Fxml loader
        //FXMLLoader loginPaneLoader = new FXMLLoader(loginController.class.getResource("login.fxml"));

        // Packaged Fxml Loader
        FXMLLoader loginPaneLoader = new FXMLLoader(getClass().getResource("/login/login.fxml"));

        Parent loginPane = loginPaneLoader.load();
        Scene loginScene = new Scene(loginPane, 1280, 800);


        // Sets Home Stage

        // IDE Fxml loader
        //FXMLLoader homePaneLoader = new FXMLLoader(homeController.class.getResource("/softwares.fxml"));

        // Packaged Fxml Loader
        FXMLLoader homePaneLoader = new FXMLLoader(getClass().getResource("/home/softwares.fxml"));

        Parent homePane = homePaneLoader.load();
        Scene homeScene = new Scene(homePane, 1280, 800);

        // Sets Install Stage


        // IDE Fxml Loader
        //FXMLLoader installPaneLoader = new FXMLLoader(installController.class.getResource("/install.fxml"));
        // Packaged Fxml Loader
        FXMLLoader installPaneLoader = new FXMLLoader(getClass().getResource("/install/install.fxml"));

        Parent installPane = installPaneLoader.load();
        Scene installScene = new Scene(installPane, 1280, 800);

        // injecting home scene into the controller of the login scene
        loginController loginPaneController = loginPaneLoader.getController();
        loginPaneController.setHomeScene(homeScene);

        // injecting install scene into the homeController
        homeController homePaneController = homePaneLoader.getController();
        homePaneController.setInstallScene(installScene);

        // injecting softwares scene into the installController
        installController installPaneController = installPaneLoader.getController();
        installPaneController.setInstallScene(homeScene);

        // Parent Stage Properties...
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setResizable(false); // makes app non resizable
        primaryStage.setTitle("Auto-Installer");
        primaryStage.setScene(loginScene);
        primaryStage.show();

    }

    public static void main(String[] args) throws DbxException, IOException {
        launch(args);

    }

}
