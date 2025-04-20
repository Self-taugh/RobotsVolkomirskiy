package gui;

import Structures.ForArgs;
import Structures.Serializing;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class HelperWindow extends JInternalFrame implements Observer, Serializing {

    private JLabel lab;
    public HelperWindow(){
        super("Окно помощи", false, true);
        setSize(150,50);

        JPanel panel = new JPanel(new BorderLayout());
        lab = new JLabel("TEST");
        panel.add(lab);
        getContentPane().add(panel);
    }


    @Override
    public void update(Observable o, Object arg) {
        ForArgs data = (ForArgs)(arg);
        String x = Double.toString(data.x);
        String y = Double.toString(data.y);
        if (x.length() > 6){
            x = x.substring(0,6);
        }
        if (y.length() > 6){
            y = y.substring(0,6);
        }
        lab.setText(x + "     " + y);
        repaint();
    }

    @Override
    public String Serialize() {
        Rectangle bounds = getNormalBounds();
        String line = getTitle()+"/";
        line = line + Integer.toString(bounds.x) + "-" + Integer.toString(bounds.y) + "-" +
                Integer.toString(bounds.height) + "-" + Integer.toString(bounds.width) + "\n";
        return line;
    }

    @Override
    public void Deserializing(String line) {
        String title;
        int x,y,height,width;
        title = line.split("/")[0];
        x = Integer.parseInt(line.split("/")[1].split("-")[0]);
        y = Integer.parseInt(line.split("/")[1].split("-")[1]);
        height = Integer.parseInt(line.split("/")[1].split("-")[2]);
        width = Integer.parseInt(line.split("/")[1].split("-")[3]);
        setBounds(x,y,width,height);
    }
}
