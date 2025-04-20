package gui;

import java.awt.*;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import Structures.Serializing;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener, Serializing
{
    private LogWindowSource m_logSource;
    private TextArea m_logContent;

    public LogWindow(LogWindowSource logSource) 
    {
        super("Протокол работы", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
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
