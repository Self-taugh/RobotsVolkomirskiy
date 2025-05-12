package gui.logicElement;

import Structures.ForArgs;

import java.awt.Point;
import java.util.*;

public class GameCore extends Observable
{
    private final Timer m_timer = initTimer();
    
    private static Timer initTimer() 
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
    
    protected volatile double m_robotPositionX = 100;
    protected volatile double m_robotPositionY = 100;
    protected volatile double m_robotDirection = 0;

    protected volatile int m_targetPositionX = 150;
    protected volatile int m_targetPositionY = 100;
    
    private static final double maxVelocity = 0.1; 
    private static final double maxAngularVelocity = 0.001;
    private static final int duration = 10;
    private static double maxRotationRadius;

    private static List<Observer> observers = new ArrayList<Observer>();


    public GameCore()
    {
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);

        FindOutRadius();
    }

    public void FindOutRadius(){
        double startAngle = Math.PI/2;
        double xDistanceDelta = 0;
        while (startAngle > 0){
            xDistanceDelta = xDistanceDelta + maxVelocity / maxAngularVelocity *
                    (Math.sin(startAngle  + maxAngularVelocity * duration) -
                            Math.sin(startAngle));
            startAngle -= maxAngularVelocity * duration;
        }
        maxRotationRadius = xDistanceDelta;
    }

    public void Subscribe(Observer obs){
        observers.add(obs);
    }

    public double[] GetCords(){
        return new double[] {m_robotPositionX, m_robotPositionY};
    }

    public void setTargetPosition(Point p)
    {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }

    private void updateListeners(Object arg){
        for (Observer obs : observers){
            obs.update(this, arg);
        }
    }

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }
    
    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;
        
        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }
    
    protected void onModelUpdateEvent()
    {
        double distance = distance(m_targetPositionX, m_targetPositionY, 
            m_robotPositionX, m_robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        angleToTarget -= m_robotDirection;
        angleToTarget = angleToTarget  * 180 / Math.PI;
        if (Math.abs(angleToTarget) > 180){
            angleToTarget -= 360;
        }
        double angularVelocity = 0;
        if (angleToTarget > 0)
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < 0)
        {
            angularVelocity = -maxAngularVelocity;
        }
        if (Math.abs(angleToTarget) > 90){
            angularVelocity = maxAngularVelocity;
        }

        // Определение невозможного цикла
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection  + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) -
                        Math.cos(m_robotDirection));

        double nowX = m_robotPositionX; double nowY = m_robotPositionY;

        double newX2 = newX + velocity / angularVelocity *
                (Math.sin(m_robotDirection  + angularVelocity * duration * 2) -
                        Math.sin(m_robotDirection + angularVelocity * duration));
        double newY2 = newY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) -
                        Math.cos(m_robotDirection));

        Structures.Point centre = CircleCenterFinder.findCircleCenters(
                new Structures.Point(nowX, nowY),
                new Structures.Point(newX, newY),
                new Structures.Point(newX2, newY2), maxRotationRadius);

        if ((Math.pow(m_targetPositionX - centre.x, 2) + Math.pow(m_targetPositionY - centre.y, 2)) <= Math.pow(maxRotationRadius, 2)){
            angularVelocity = 0;
        }

        updateListeners(new ForArgs(m_robotPositionX, m_robotPositionY, m_robotDirection,
                m_targetPositionX, m_targetPositionY));

        moveRobot(velocity, angularVelocity, duration);
    }
    
    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
    
    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity * 
            (Math.sin(m_robotDirection  + angularVelocity * duration) -
                Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity * 
            (Math.cos(m_robotDirection  + angularVelocity * duration) -
                Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration); 
        m_robotDirection = newDirection;
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }
    
    private static int round(double value)
    {
        return (int)(value + 0.5);
    }
}
