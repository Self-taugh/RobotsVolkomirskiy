package gui.windows;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import gui.logicElement.MenuMaker;
import gui.logicElement.Loader;
import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
        //Большое окно с отступом в 50 пикселей
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);


        //Генерация окон
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        HelperWindow helperWindow = new HelperWindow();
        addWindow(helperWindow);
        gameWindow.model.Subscribe(helperWindow);

        //Генерация меню
        MenuMaker menuMaker = new MenuMaker(this);
        setJMenuBar(menuMaker.MakeMenu());


        //Настройки окна
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                Loader.Load(desktopPane);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showOptionDialog(desktopPane,"Уверены?", "Выход",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        new Object[]{"Да", "Нет"}, null);
                if (answer == 0){
                    Loader.Save(desktopPane);
                    System.exit(1);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }






    //Оставлю так. Не придумал как убрать
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
