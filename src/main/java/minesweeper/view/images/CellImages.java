package minesweeper.view.images;

import minesweeper.view.GUIField;

import javax.swing.*;
import java.awt.*;

public enum CellImages {
    EMPTY,
    NUMBER1,
    NUMBER2,
    NUMBER3,
    NUMBER4,
    NUMBER5,
    NUMBER6,
    NUMBER7,
    NUMBER8,
    MINE,
    MINE_EXPLODED,
    MINE_MISTAKE,
    CLOSED,
    CLOSED_FLAG,
    CLOSED_QUESTION;

    private ImageIcon icon;

    public ImageIcon getIcon() {
        return icon;
    }

    public static void setImages() {
        for (CellImages images : CellImages.values()) {
            Image image = ImageLoader.loadImage(images, GUIField.getCellSize());
            if (image != null) {
                images.icon = new ImageIcon(image);
            }
        }
    }
}
