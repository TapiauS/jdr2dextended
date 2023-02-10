package Graphic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInterface extends JFrame implements ActionListener {

    public LogInterface(){
        super();
        buildContentPane();
    }


    private  JPanel buildContentPane(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton bouton = new JButton("Cliquez ici !");
        panel.add(bouton);

        JButton bouton2 = new JButton("Ou là !");
        panel.add(bouton2);

        return panel;


    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
