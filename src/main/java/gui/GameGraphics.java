package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;

public class GameGraphics extends JPanel implements Observer {

    private final GameCore model;

    public GameGraphics(){
        model = new GameCore();
        model.Subscribe(this);

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                model.setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    public void subscribeToModel(Observer obs){
        model.Subscribe(obs);
    }

    private static int round(double value)
    {
        return (int)(value + 0.5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    // Cords[0] - m_robotPositionX Cords[1] - m_robotPositionY Cords[2] - direction
    private void drawRobot(Graphics2D g, double[] Cords)
    {
        int robotCenterX = round(Cords[0]);
        int robotCenterY = round(Cords[1]);
        AffineTransform t = AffineTransform.getRotateInstance(Cords[2], robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }



    // Cords[0] - m_robotPositionX    Cords[1] - m_robotPositionY
    // Cords[2] - m_robotDirection   Cords[3] - m_targetPositionX   Cords[4] - m_targetPositionY
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        double[] Cord2 = {model.m_robotPositionX, model.m_robotPositionY, model.m_robotDirection};
        drawRobot(g2d,  Cord2);
        drawTarget(g2d, (int)(model.m_targetPositionX), (int)(model.m_targetPositionY));
    }
}

