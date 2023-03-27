package Graphic;

import Control.ChatProtocol;

import javax.swing.*;

public class ChatGraphicInterface extends JFrame {

    private JTextField chat;

    private JPanel contentpane;
    private JTextArea historic;

    private JScrollPane scroller;

    private ChatProtocol chatprotocol;

    private GameInterface fenetre;

    public ChatGraphicInterface(){
        //this.fenetre=fenetre;
        contentpane=new JPanel();
        this.setContentPane(contentpane);
        chat=new JTextField("Ecrire votre message ici");
        historic=new JTextArea("Aevvzhzgvghvrhgzvgrvhavrhaevvahgrezvahgrvhgvrhgrvhgzervgahzrvehgrvahgzvr" +
                "hgezvrhgezvrehgzvrhgezrvhgzervahgzrvahgzervhgzrvahgzrvahgz");
        historic.setColumns(20);
        historic.setEditable(false);
        historic.setVisible(true);
        scroller=new JScrollPane(historic);
        contentpane.add(scroller);
        contentpane.add(chat);
        pack();
        this.setVisible(true);
    }

    public static void main(String [] args){
        new ChatGraphicInterface();
    }
}
