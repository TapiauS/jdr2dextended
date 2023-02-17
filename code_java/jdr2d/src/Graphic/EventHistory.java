package Graphic;

import javax.swing.*;

public class EventHistory extends JLabel {
    String events;

    int first_line;

    int nb_displayed_line;

    JTextArea historydisplayer;

    public static final int EVENT_WIDTH= (int) (GameInterface.WINDOW_WIDTH*0.2);

    public static final int EVENT_HEIGH= (int) (GameInterface.WINDOWS_HEIGH*0.2);

    //builders

    public EventHistory(){
        super();
        events=new String("Bienvenu dans afpanum");
        this.setText(events);
        this.setBounds(0,GameInterface.WINDOW_WIDTH-EVENT_WIDTH,MapPanel.MAP_WIDTH,EVENT_HEIGH);
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
        setEvents(events+'\n'+line);
        this.setText(events);
        this.repaint();
        this.revalidate();
    }

}
