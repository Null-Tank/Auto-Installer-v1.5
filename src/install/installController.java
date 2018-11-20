package install;
import com.dropbox.core.DbxException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.Program;
import server.Data;

import java.io.IOException;
import java.util.List;
import com.dropbox.core.DbxException;
import com.fasterxml.jackson.core.JsonProcessingException;

import static home.homeController.softwareToBeInstalled;


public class installController {
    private Scene softwaresScene;
    public ObjectMapper mapper = new ObjectMapper();
    private Data data = new Data();

    private @FXML AnchorPane anchorPane;
    public @FXML ProgressBar installationProgress;
    public @FXML Text progressText;
    public @FXML ScrollPane scroll_pane;

    public static VBox softwareListContainer = new VBox();

//    public static Pane softwareListContainer = new Pane();


    public static List softwareList;

    public static void setSoftwaresList(List softwares){
        softwareList = softwares;
        System.out.println(softwareList);
    }


    // Installs programs from dropbox
    @FXML private void installSoftwares() throws JsonProcessingException, IOException, DbxException {
        try{
            for (int i = 0; i < softwareList.size(); i++) {
                Object selectedProgram = softwareList.get(i);
                String programJSON = mapper.writeValueAsString(selectedProgram);
                Program program = mapper.readValue(programJSON, Program.class);
                data.getDropboxFile((program.name).toString(), (program.category).toString(), ".zip", (program.exeName).toString(), installationProgress, progressText);
            }

        }catch(NullPointerException ex){
            progressText.setText("You haven't selected any software to Install!");
        }

        System.out.println(softwareListContainer);

    }

    public void setInstallScene(Scene scene){
        softwaresScene = scene;
    }

    @FXML private void routeToSoftwares(){
        Stage primaryStage = (Stage) anchorPane.getScene().getWindow();
        primaryStage.setScene(softwaresScene);
        System.out.println("Routing to softwares..");
    }

    public static Pane creatSelectedSoftwareNode(Program program){

        // Selected Software
        Pane selectedSoftware = new Pane();
        selectedSoftware.setPrefHeight(128.0);
        selectedSoftware.setMaxWidth(1280.0);
        selectedSoftware.setMaxHeight(128.0);
        selectedSoftware.setMaxWidth(1280.0);
        selectedSoftware.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #DCDCDC; -fx-border-width: 0 0 2 0;");

        Text softwareName = new Text();
        softwareName.setLayoutX(140.0);
        softwareName.setLayoutY(40.0);
        softwareName.setFont(Font.font ("Abel Regular", 27));
        softwareName.setText((program.name).toString());

        Text softwareMemSize = new Text();
        softwareMemSize.setLayoutX(140.0);
        softwareMemSize.setLayoutY(67.0);
        softwareMemSize.setFont(Font.font ("Abel Regular", 18));
        softwareMemSize.setText("Memory Size: " + (program.name).toString());

        Text softwareVersion = new Text();
        softwareVersion.setLayoutX(140.0);
        softwareVersion.setLayoutY(92.0);
        softwareVersion.setFont(Font.font ("Abel Regular", 18));
        softwareVersion.setText("Version: " + (program.name).toString());

        Text cancelButton = new Text();
        cancelButton.setLayoutX(1189.0);
        cancelButton.setLayoutY(71.0);
        cancelButton.setFont(Font.font ("Abel Regular", 27));
        cancelButton.setText("X");
        cancelButton.setFill(Color.RED);

        // Handles what will occur when the software is selected
        cancelButton.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                softwareList.remove(program);
                softwareToBeInstalled.remove(program);
                System.out.println("removed: " + program);

                GridPane homeSoftwareDisplay = home.homeController.softwareDisplay;

                for(int i = 0; i < homeSoftwareDisplay.getChildren().size(); i++){

                    if(homeSoftwareDisplay.getChildren().get(i).getId().equals((program.name).toString())){
                        System.out.println("Found: " + (program.name).toString());
                    }else{
                        System.out.println("id: " + homeSoftwareDisplay.getChildren().get(i).getId() + " & program-name: " + (program.name).toString());
                    }
                }

            }
        });

        // Software Image
        ImageView softwareImgContainer = new ImageView();

        Image softwareImg = new Image("./icons/" + program.img_dir);
        softwareImgContainer.setImage(softwareImg);

        System.out.println("Issue loading Image? " + softwareImg.isError());

        softwareImgContainer.setFitHeight(128.0);
        softwareImgContainer.setFitWidth(128.0);
        softwareImgContainer.setSmooth(true);
        softwareImgContainer.setCache(true);
        softwareImgContainer.setPreserveRatio(true);

        selectedSoftware.getChildren().addAll(cancelButton, softwareVersion, softwareMemSize, softwareName, softwareImgContainer);

        return selectedSoftware;
    }

    public static void displaySelectedSoftware(VBox softwareListContainer){

        softwareListContainer.getChildren().clear();

        ObjectMapper mapper = new ObjectMapper();

        Pane softwareNode = null;

        softwareListContainer.setPrefHeight(128.0 * softwareList.size());

        try{
            for (int i = 0; i < softwareList.size(); i++) {
                Object selectedProgram = softwareList.get(i);
                String programJSON = mapper.writeValueAsString(selectedProgram);
                Program program = mapper.readValue(programJSON, Program.class);
                softwareNode =  creatSelectedSoftwareNode(program);
                softwareListContainer.getChildren().add(softwareNode);
            }

        }catch(JsonParseException ex){
            System.out.println("Error: " + ex);
        }catch(JsonProcessingException ex){
            System.out.println("Error: " + ex);
        }
        catch(IOException ex){
            System.out.println("Error: " + ex);
        }

    }

    @FXML
    protected void initialize() {
        softwareListContainer.setPrefWidth(1280.0);
        softwareListContainer.setPrefHeight(128.0);
        softwareListContainer.setLayoutY(72.0);
        softwareListContainer.setSpacing(0.0);


        scroll_pane.setContent(softwareListContainer);

    }

}
