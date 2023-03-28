package Graphic;

import Control.ChatClientProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class ChatGraphicInterface extends JFrame{

    private JTextField chat;

    private JPanel contentpane;
    private JTextArea historic;

    private JScrollPane scroller;

    private JButton validation;
    private ChatClientProtocol chatprotocol;

    private GameInterface fenetre;

    private boolean isfirstmodification=true;

    public ChatGraphicInterface(GameInterface fenetre) throws IOException {
        super();
        this.fenetre=fenetre;
        this.setIconImage(new ImageIcon("Portraits/gamicon.png").getImage());
        contentpane=new JPanel(new GridBagLayout());
        GridBagConstraints constraints=new GridBagConstraints();
        this.setContentPane(contentpane);
        chat=new JTextField("Ecrire votre message ici");
        chat.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(isfirstmodification) {
                    chat.setText("");
                    chat.setColumns(10);
                    isfirstmodification=false;
                    repaint();
                    revalidate();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==10&&!chat.getText().isEmpty()&&!isfirstmodification)
                {
                    try {
                        String msg=fenetre.getUtil().getNomUtilisateur()+" : "+chat.getText();
                        chatprotocol.sendmessage(msg);
                        repaint();
                        revalidate();
                        pack();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        historic=new JTextArea();
        historic.setColumns(20);
        historic.setRows(10);
        historic.setEditable(false);
        historic.setVisible(true);
        scroller=new JScrollPane(historic);
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.gridwidth=2;
        contentpane.add(scroller,constraints);
        constraints.gridx=0;
        constraints.gridy=1;
        constraints.gridwidth=1;
        contentpane.add(chat,constraints);
        validation=new JButton("Valider");
        validation.addActionListener(e->{
            if(!chat.getText().isEmpty()&&!isfirstmodification) {
                try {
                    chatprotocol.sendmessage(fenetre.getUtil().getNomUtilisateur()+" : "+chat.getText());
                    repaint();
                    revalidate();
                    pack();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        constraints.gridx=1;
        constraints.gridy=1;
        contentpane.add(validation,constraints);
        chatprotocol=new ChatClientProtocol(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
        pack();
    }

    public void addline(String msg){
        historic.setText(historic.getText()+msg+'\n');
    }
}
