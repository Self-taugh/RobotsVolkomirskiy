package gui;

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
                System.out.println(line);
                String title;
                int x,y,height,width;
                title = line.split("/")[0];
                x = Integer.parseInt(line.split("/")[1].split("-")[0]);
                y = Integer.parseInt(line.split("/")[1].split("-")[1]);
                height = Integer.parseInt(line.split("/")[1].split("-")[2]);
                width = Integer.parseInt(line.split("/")[1].split("-")[3]);
                for (JInternalFrame frame : pane.getAllFrames()){
                    if (title.equals(frame.getTitle())){
                        frame.setBounds(x,y,width,height);
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
                Rectangle bounds = frame.getNormalBounds();
                String line = frame.getTitle()+"/";
                line = line + Integer.toString(bounds.x) + "-" + Integer.toString(bounds.y) + "-" +
                Integer.toString(bounds.height) + "-" + Integer.toString(bounds.width) + "\n";
                writer.write(line);
            }
            writer.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(pane, "Ошибка сохранения конфигурации");
        }
    }
}
