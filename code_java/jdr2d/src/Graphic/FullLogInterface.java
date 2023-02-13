package Graphic;

import Control.ConnexionButton;
import Control.CreateAccountButton;
import DAO.PersonnageDAO;
import jdr2dcore.Map;
import jdr2dcore.Personnage;

import javax.swing.*;
import java.awt.*;

public class FullLogInterface extends JFrame {
    private Component toptextfield;
    private JLabel toplabel;

    private JButton top;

    private JTextField bottomtextfield;

    private JLabel bottomlabel;

    private JButton bottom;

    private Personnage perso;

    private Map map;


    public FullLogInterface(){
        super();

        build();//On initialise notre fenêtre
    }


    //getters


    public Map getMap() {
        return map;
    }

    public Personnage getPerso() {
        return perso;
    }

    public Component getToptextfield(){
        return toptextfield;
    }

    public JLabel getToplabel(){
        return toplabel;
    }

    public JButton getBottom() {
        return bottom;
    }

    public JButton getTop() {
        return top;
    }

    public JLabel getBottomlabel() {
        return bottomlabel;
    }

    public JTextField getBottomtextfield() {
        return bottomtextfield;
    }



    //setters

    public void setMap(Map map) {
        this.map = map;
    }

    public void setToptextfield(Component textefield){
        swap(this.toptextfield,textefield);
        this.toptextfield =textefield;
    }



    public void setToplabel(JLabel toplabel) {
        swap(this.toplabel,toplabel);
        this.toplabel = toplabel;
    }

    public void setBottom(JButton bottom) {
        swap(this.bottom,bottom);
        this.bottom = bottom;
    }

    public void setTop(JButton top) {
        swap(this.top,top);
        this.top = top;
    }

    public void setBottomlabel(JLabel bottomlabel) {
        swap(this.bottomlabel,bottomlabel);
        this.bottomlabel = bottomlabel;
    }

    public void setBottomtextfield(JTextField bottomtextfield) {
        swap(this.bottomtextfield,bottomtextfield);
        this.bottomtextfield = bottomtextfield;
    }

    public void setPerso(Personnage perso) {
        this.perso = perso;
    }

    //methodes


    private void swap(Component previouscomp,Component nextcomp){
        if(previouscomp!=null&&nextcomp!=null) {
            GroupLayout group = (GroupLayout) this.getContentPane().getLayout();
            group.replace(previouscomp,nextcomp);
        }
    }


    private void build() {
        setTitle("Afpanums"); //On donne un titre à l'application
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        setContentPane(buildContentPane());
        pack();
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(true);
    }

    private JPanel buildContentPane(){
        JPanel panel = new JPanel();
        GroupLayout group=new GroupLayout(panel);
        panel.setLayout(group);
        group.setAutoCreateGaps(true);
        group.setAutoCreateContainerGaps(true);
        JButton create=new JButton(new CreateAccountButton(this,"Creer un compte"));
        top=create;
        bottom=new JButton(new ConnexionButton(this,"Connexion"));
        bottomlabel=new JLabel();
        bottomtextfield=new JTextField();

        toptextfield =new JTextField(10);
        toplabel=new JLabel();

        toplabel.setVisible(false);
        toptextfield.setVisible(false);

        bottomlabel.setVisible(false);
        bottomtextfield.setVisible(false);
        bottom.setVisible(true);


        group.setHorizontalGroup(group.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(group.createSequentialGroup()
                        .addComponent(toplabel)
                        .addComponent(toptextfield)
                        .addComponent(top))
                .addGroup(group.createSequentialGroup()
                        .addComponent(bottomlabel)
                        .addComponent(bottomtextfield)
                        .addComponent(bottom)
                ));

        group.setVerticalGroup(group.createSequentialGroup()
                .addGroup(group.createParallelGroup()
                        .addComponent(toplabel)
                        .addComponent(toptextfield)
                        .addComponent(top))
                .addGroup(group.createParallelGroup()
                                .addComponent(bottomlabel)
                                .addComponent(bottomtextfield)
                                .addComponent(bottom)));

        return panel;
    }

}
