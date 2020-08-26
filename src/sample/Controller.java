package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private Model model;
    private Desktop desktop = Desktop.getDesktop();
    public Controller(){
        this.model = new Model(new BasicEncoder(),new BasicDecoder());
    }
    @FXML
   private ImageView originImage;
    @FXML
   private TextArea fieldMessage;
    @FXML
    private ImageView originImage2;
    @FXML
    private TextArea fieldMessage2;
    @FXML
    private ImageView modifiedImage;
    @FXML
    private Button bt_saveE;
    @FXML
    private Button bt_OpenE;
    @FXML
    private VBox vbAnh;
    @FXML
    private Button bt_OpenD;

    public void onEncode() {
        try {
            Image modified = model.encode(originImage.getImage(), fieldMessage.getText());
            modifiedImage.setImage(modified);
            bt_saveE.setDisable(false);
        }
        catch (NullPointerException ex ){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lỗi khi mã hóa");

            // Header Text: null
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn ảnh trước khi mã hóa !!");

            alert.showAndWait();
        }
    }
    @FXML
    private void handlerSaveClick( ActionEvent event){
        Window stage = new Stage();
        bt_saveE.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Image");
                configureFileChooser(fileChooser);
                //System.out.println(modifiedImage.getId());
                File file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(modifiedImage.getImage(),
                            null), "png", file);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }

    @FXML
    private  void handlerOpenClick(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        Window stage = new Stage();
        bt_OpenE.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    openFile(file);
                }
            }
        });
    }

    @FXML
    private  void handlerDeCodeOpenClick(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        Window stage = new Stage();
        bt_OpenD.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    openDecodeFile(file);
                }
            }
        });
    }

    public void Encode(ActionEvent event){
        System.out.println("Clicked......");
    }


    public void onDecode() {
        String message = model.decode(originImage2.getImage());
        //System.out.println(message);
        fieldMessage2.setText(message);
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private void openFile(File file) {
        try {
            //desktop.open(file);
            Image image = new Image (file.toURI().toString());
            originImage.setImage(image);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

    private void openDecodeFile(File file) {
        try {
            //desktop.open(file);
            Image image = new Image (file.toURI().toString());
            originImage2.setImage(image);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }
}
