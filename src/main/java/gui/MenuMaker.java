package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class MenuMaker {
    JFrame frame;
    public MenuMaker(JFrame fram){
        frame=fram;
    }

    public JMenuBar MakeMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = Display();
        JMenu testMenu = Test();
        JMenu closeTable = Close();

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(closeTable);
        return menuBar;
    }

    private JMenu Display(){
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                frame.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                frame.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }
        return lookAndFeelMenu;
    }

    private JMenu Test(){
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }
        return testMenu;
    }


    private JMenu Close(){
        JMenu closingTable = new JMenu("Закрыть");
        closingTable.setMnemonic(KeyEvent.VK_C);
        closingTable.getAccessibleContext().setAccessibleDescription("Закрыть приложение");

        JMenuItem closeButton = new JMenuItem("Выключить приложение", KeyEvent.VK_C);
        closeButton.addActionListener((event) -> {
            int answer = JOptionPane.showConfirmDialog(frame, "Уверены?");
            if (answer == 0){
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }

        });
        closingTable.add(closeButton);
        return  closingTable;
    }

    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(frame);
        }
        catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
