package gui;

import Structures.ForHelper;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Observable;
import java.util.Observer;

public class HelperWindow extends JInternalFrame implements Observer {

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
        ForHelper data = (ForHelper)(arg);
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
}
