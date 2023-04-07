package Control;

import Graphic.FullLogInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class DeleteCharAction extends AbstractAction {


    FullLogInterface fenetre;

    public DeleteCharAction(FullLogInterface fenetre,String message){
        super(message);
        this.fenetre=fenetre;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JList<String> list = (JList<String>) this.fenetre.getToptextfield();
        System.out.println(list.getModel().getClass());
        String charname =list.getSelectedValue();
        if (list.getSelectedIndex() > -1){
            try {
                String[] updateddata=new String[list.getModel().getSize()-1];
                boolean found=false;
                for (int i = 0; i < list.getModel().getSize(); i++) {
                    if(i==list.getSelectedIndex()){
                        i++;
                        found=true;
                    }
                    else {
                        if (!found)
                            updateddata[i]= list.getModel().getElementAt(i);
                        else
                            updateddata[i-1]=list.getModel().getElementAt(i);
                    }
                    list.setListData(updateddata);
                }
                ClientPart.write(ConnexionOutput.DELETECHAR);
                ClientPart.write(charname);
                fenetre.refresh();
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
    }
}
