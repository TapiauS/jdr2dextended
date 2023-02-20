package Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EventHistory extends JTextArea {
    String events;

    int nb_displayed_line;

    JTextArea historydisplayer;

    public static final int EVENT_WIDTH= (int) (GameInterface.WINDOW_WIDTH*0.6);

    public static final int EVENT_HEIGH= (int) (GameInterface.WINDOWS_HEIGH*0.4);

    //builders

    public EventHistory(){
        super();
        this.setBackground(Color.white);
        events=new String("");
        nb_displayed_line=0;
        this.setText(events);
        this.setRequestFocusEnabled(false);
        this.setBounds(0,GameInterface.WINDOW_WIDTH-EVENT_HEIGH,EVENT_WIDTH,EVENT_HEIGH);
        this.setVisible(true);
    }

    //getters


    public String getEvents() {
        return events;

    }

    //setters


    public void setEvents(String events) {
        this.events = events;
    }


    //methodes

    public void addLine(String line){
        nb_displayed_line++;
        if(nb_displayed_line<EVENT_HEIGH/10)
            setEvents(events+'\n'+line);
        this.setText(events);
        this.repaint();
        this.revalidate();
    }

}
