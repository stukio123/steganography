package sample;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;

import java.util.stream.IntStream;

public class BasicEncoder implements Encoder {
    @Override
    public Image encode(Image image, String message) {

        int width = (int) image.getWidth(); //Lấy chiều rộng của ảnh
        int height = (int) image.getHeight(); // Lấy chiều cao của ảnh

        WritableImage copy = new WritableImage(image.getPixelReader(), width, height); // Copy ảnh gốc sang 1 ảnh mới

        PixelWriter writer = copy.getPixelWriter();
        PixelReader reader = image.getPixelReader();


        boolean[] bits = encode(message); // Chuyển Thông điệp sang mảng các bit

        IntStream.range(0, bits.length)
                .mapToObj(i -> new Pair<>(i, reader.getArgb(i % width, i / width))) // Đọc chỉ số ARGB
                .map(pair -> new Pair<>(pair.getKey(), bits[pair.getKey()] ? pair.getValue() | 1 : pair.getValue() &~ 1))
                .forEach(pair -> {
                    int x = pair.getKey() % width;
                    int y = pair.getKey() / width;
                    writer.setArgb(x, y, pair.getValue());
                });

        return copy;
    }


    /*
    Ta có 1 mảng Byte chứa thông tin cần giấu
    */
    private boolean[] encode(String message) {
        byte[] data = message.getBytes();

        /* Kiểu int dùng 2 đến 4 btye để lưu trữ => int = 32 bits (Dùng để chứa độ dài của thông điệp)
         byte = 8 bits
         => thông điệp cần có ( 32 + độ dài thông điệp * 8 ) vị trí để lưu trữ
         */
        boolean[] bits = new boolean[32 + data.length * 8];

        // chuyển đổi độ dài của thông điệp sang chuỗi gồm các số nhị phân
        String binary = Integer.toBinaryString(data.length);

        // Mã hóa độ dài thông điệp
        while (binary.length() < 32) {
            binary = "0" + binary;
        }

        for (int i = 0; i < 32; i++) {
            bits[i] = binary.charAt(i) == '1';
        }

        // vị trí của các bit [7, 6, 5 ... 0]
        // Mã hóa thông điệp
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            for (int j = 0; j < 8; j++) {
                bits[32 + i*8 + j] = ((b >> (7 - j)) & 1) == 1;
            }
        }

        return bits;
    }
}
