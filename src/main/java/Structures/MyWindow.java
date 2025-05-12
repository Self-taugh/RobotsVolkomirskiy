package Structures;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;

public abstract class MyWindow extends JInternalFrame{

    public MyWindow(String title, boolean r, boolean c, boolean m){
        super(title,r,c,m);
    }

    public String Serialize(){
        GSONSubclas g = new GSONSubclas(getNormalBounds(),getTitle());
        Gson gson = new Gson();
        String t = gson.toJson(g);
        return t;
    }
    public void Deserializing(GSONSubclas gsonSubclas){
        setBounds(gsonSubclas.rectangle);
    }
}
