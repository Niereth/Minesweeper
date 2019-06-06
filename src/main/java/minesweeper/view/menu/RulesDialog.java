package minesweeper.view.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

class RulesDialog {
    private static final Logger log = LoggerFactory.getLogger(RulesDialog.class);
    private JDialog dialog;

    RulesDialog() {
        initDialog();
    }

    private void initDialog() {
        JFrame frame;
        JPanel background;

        frame = new JFrame();
        dialog = new JDialog(frame, "Правила игры", true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        JTextArea rulesTextArea = new JTextArea(39,70);
        rulesTextArea.setLineWrap(true);
        rulesTextArea.setWrapStyleWord(true);
        rulesTextArea.setEditable(false);

        String directory = "rules/";
        String fileName = "rules";
        File file = loadFileFromResources(directory, fileName);
        if (file == null) {
            return;
        }

        try {
            FileReader reader = new FileReader(file);
            rulesTextArea.read(reader, fileName);
        } catch (IOException e) {
            log.info("Не удалось открыть правила игры.", e);
        }

        background = new JPanel();
        background.add(rulesTextArea);

        dialog.add(background);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
    }

    private File loadFileFromResources(String directory, String fileName) {
        String relativePath = directory + fileName;
        URL absolutePath = RulesDialog.class.getClassLoader().getResource(relativePath);
        if (absolutePath == null) {
            log.error("Заданной директории не существует");
            return null;
        }

        URI uri;
        try {
            uri = absolutePath.toURI();
        } catch (URISyntaxException e) {
            log.error("Ошибка при создании URI",e);
            return null;
        }

        return new File(uri);
    }

    void openDialog() {
        dialog.setVisible(true);
    }
}
