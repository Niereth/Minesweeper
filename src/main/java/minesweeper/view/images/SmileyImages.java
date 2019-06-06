package minesweeper.view.images;

import minesweeper.view.GUIField;

import javax.swing.*;
import java.awt.*;

public enum SmileyImages {
    SMILEY_SMILE,
    SMILEY_WINNER,
    SMILEY_LOSER;

    private ImageIcon icon;

    public ImageIcon getIcon() {
        return icon;
    }

    public static void setImages() {
        for (SmileyImages images : SmileyImages.values()) {
            Image image = ImageLoader.loadImage(images, (int) (1.5 * GUIField.getCellSize()));
            if (image != null) {
                images.icon = new ImageIcon(image);
            }
        }
    }
}
