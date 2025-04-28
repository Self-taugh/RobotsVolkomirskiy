package gui.windows;

import Structures.MyWindow;
import gui.logicElement.GameCore;
import gui.logicElement.GameGraphics;

import java.awt.*;

import javax.swing.JPanel;

public class GameWindow extends MyWindow
{
    public final GameGraphics m_visualizer;
    public final GameCore model;
    public GameWindow()
    {
        super("Игровое поле", true, true, true);
        model = new GameCore();
        m_visualizer = new GameGraphics(model);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        //pack();*/
    }

    /*@Override
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
    }*/
}
