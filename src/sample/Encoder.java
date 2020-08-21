package sample;


import javafx.scene.image.Image;

public interface Encoder {
    /*
        @params image  : Đường dẫn của ảnh
        @params message : Thông tin muốn dấu
        @returns
     */
    Image encode(Image image, String message);
}
