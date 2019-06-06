package minesweeper.view.images;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

class ImageLoader {
    private static final Logger log = LoggerFactory.getLogger(ImageLoader.class);

    private ImageLoader() {
    }

    static Image loadImage(Enum images, int size) {
        String filename = images.name().toLowerCase();
        String relativePath = "images/" + filename + ".png";
        URL absolutePath = ImageLoader.class.getClassLoader().getResource(relativePath);
        if (absolutePath != null) {
            ImageIcon source = new ImageIcon(absolutePath);
            return source.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        } else {
            log.error("Файл картинки не найден.");
            return null;
        }
    }
}