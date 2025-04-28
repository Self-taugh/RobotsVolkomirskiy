package gui.logicElement;



import Structures.GSONSubclas;
import Structures.MyWindow;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Loader {
    public static void Load(JDesktopPane pane){
        System.out.println(System.getProperty("user.home"));
        try {
            File f = new File(System.getProperty("user.home") + "\\RobotsVolkomirskiyConfig.json");
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                Gson gson = new Gson();
                GSONSubclas gsonSubclas = gson.fromJson(line, GSONSubclas.class);
                for (JInternalFrame frame : pane.getAllFrames()){
                    if (gsonSubclas.title.equals(frame.getTitle())){
                        MyWindow m = (MyWindow) frame;
                        m.Deserializing(gsonSubclas);
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
            File f = new File(System.getProperty("user.home") + "\\RobotsVolkomirskiyConfig.json");
            FileWriter writer = new FileWriter(f, false);

            for (JInternalFrame frame : pane.getAllFrames()){
                MyWindow m = (MyWindow) frame;
                String s = m.Serialize();
                writer.write(s + "\n");
            }
            writer.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(pane, "Ошибка сохранения конфигурации");
        }
    }
}
