package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {

    private Model model;

    public Controller(){
        this.model = new Model(new BasicEncoder(),new BasicDecoder());
    }

    @FXML
   private ImageView originImage;
    @FXML
   private TextArea fieldMessage;
    @FXML
    private ImageView modifiedImage;

    public void onEncode() {
        Image modified = model.encode(originImage.getImage(), fieldMessage.getText());
        modifiedImage.setImage(modified);
    }

    public void Encode(ActionEvent event){
        System.out.println("Clicked......");
    }

    public void onDecode() {
        String message = model.decode(modifiedImage.getImage());
        System.out.println(message);
    }
}
