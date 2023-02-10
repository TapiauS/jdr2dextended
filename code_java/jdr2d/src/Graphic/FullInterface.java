package Graphic;

import javax.swing.*;
import java.awt.*;

public class FullInterface extends JFrame {
    private JTextField textField;
    private JLabel label;


    public FullInterface(){
        super();
        build();//On initialise notre fenêtre
    }
    private void build() {
        setTitle("Afpanums"); //On donne un titre à l'application
        setSize(400, 200); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(true); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        setContentPane(buildContentPane());
    }

    private JPanel buildContentPane(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        label = new JLabel("Avez vous déja un compte ?");
        panel.add(label);
        JButton create=new JButton(new CreateAccountButton(this,"Creer un compte"));
        panel.add(create);
        return panel;
    }

    public JTextField getTextField(){
        return textField;
    }

    public JLabel getLabel(){
        return label;
    }


    public void setTextField(JTextField textefield){
        this.textField=textefield;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }
}
