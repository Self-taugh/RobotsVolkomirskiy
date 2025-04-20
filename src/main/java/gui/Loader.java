package gui;

import Structures.Serializing;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Loader {
    public static void Load(JDesktopPane pane){
        System.out.println(System.getProperty("user.home"));
        try {
            File f = new File(System.getProperty("user.home") + "\\RobotsVolkomirskiyConfig.txt");
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {

                String title;
                title = line.split("/")[0];
                for (JInternalFrame frame : pane.getAllFrames()){
                    if (title.equals(frame.getTitle())){
                        Serializing s = (Serializing)frame;
                        s.Deserializing(line);
                    }
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(pane, "Файл конфигурации не найден! Стандартная генерация");
        }
    }

    public static void Save(JDesktopPane pane){
        try{
            File f = new File(System.getProperty("user.home") + "\\RobotsVolkomirskiyConfig.txt");
            FileWriter writer = new FileWriter(f, false);

            for (JInternalFrame frame : pane.getAllFrames()){
                Serializing s = (Serializing) frame;
                writer.write(s.Serialize());
            }
            writer.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(pane, "Ошибка сохранения конфигурации");
        }
    }
}
