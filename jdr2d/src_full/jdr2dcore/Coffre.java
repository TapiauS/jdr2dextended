package jdr2dcore;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Coffre extends Objet {

    protected boolean opened;
    protected ArrayList<Objet> contenu;
    protected boolean tas;
//getters

    public ArrayList<Objet> getContenu(){
        return contenu;
    }
    //setters


    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void setTas(boolean tas) {
        this.tas = tas;
    }

    public Coffre setContenu(ArrayList<Objet> contenu){
        this.contenu=contenu;
        return this;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isTas() {
        return tas;
    }

    //methodes

    public Coffre(){
        super();
        this.contenu=new ArrayList<Objet>();
    }

    public Coffre(Objet o){
        super();
        this.contenu = new ArrayList<Objet>();
        this.add(o);
    }


    public Coffre add(Objet o){
        this.contenu.add(o);
        return this ;
    }

    public Coffre remove(Objet o){
        this.contenu.remove(o);
        return this;
    }

    public Coffre remove(int ind){
        this.contenu.remove(ind);
        return this;
    }

    //methodes

    public Objet findremove(String indecxglobal){
        int index=0;
        String reste="";
        StringBuffer valeur=new StringBuffer();
        System.out.println("Je suis dans le coffre : "+this.getNomObjet());
        while (index<indecxglobal.length() && indecxglobal.charAt(index)!='.'  ){
            valeur.append(indecxglobal.charAt(index));
            index++;
        }

        System.out.println("Valeur ="+valeur);
        if(index<indecxglobal.length()) {
            reste = indecxglobal.substring(index + 1);
        }

        if(index==indecxglobal.length()){
            int i=Integer.parseInt(String.valueOf(valeur));
            Objet o=this.getContenu().get(i);
            this.remove(o);
            return o;
        }
        else{
            System.out.println("reste= "+reste);
            Coffre o= (Coffre) this.getContenu().get(index);
            System.out.println("J'ai trouvé l'arme : " +o.find(reste).getNomObjet());
            return o.findremove(reste);
        }
    }

    public Objet find(String indecxglobal){
        int index=0;
        String reste="";
        StringBuffer valeur=new StringBuffer();

        while (index<indecxglobal.length() && indecxglobal.charAt(index)!='.'  ){
            valeur.append(indecxglobal.charAt(index));
            index++;
        }

        if(index<indecxglobal.length()) {
            reste = indecxglobal.substring(index + 1);
        }
        if(index==indecxglobal.length()){
            int i=Integer.parseInt(String.valueOf(valeur));
            Objet o=this.getContenu().get(i);
            return o;
        }
        else{
            Coffre o= (Coffre) this.getContenu().get(index);
            return o.find(reste);
        }
    }


    public LinkedHashMap<Integer,String> findweapon(){
        Integer index=0;
        int indexglobal = 0;
        LinkedHashMap<Integer,String> retour=new LinkedHashMap<>();
        for (Objet o: this.getContenu()) {
            if(o instanceof Arme){
                retour.put(index, String.valueOf(indexglobal));
                index++;
            }
            if(o instanceof Coffre){
                LinkedHashMap<Integer, String> cofreint=((Coffre) o).findweapon();
                int ajoutindex=cofreint.size();
                while(cofreint.size()>0){
                    retour.put(cofreint.keySet().stream().findFirst().get()+index,indexglobal+
                            "."+cofreint.get(cofreint.keySet().stream().findFirst().get()));
                    cofreint.remove(cofreint.keySet().stream().findFirst().get());
                }
                index=index+ajoutindex;
            }
            indexglobal++;
        }
        return retour;
    }

    public LinkedHashMap<Integer,String> findarmure(){
        Integer index=0;
        int indexglobal = 0;
        LinkedHashMap<Integer,String> retour=new LinkedHashMap<>();
        for (Objet o: this.getContenu()) {
            if(o instanceof Armure){
                retour.put(index, String.valueOf(indexglobal));
                index++;
            }
            if(o instanceof Coffre){
                LinkedHashMap<Integer, String> cofreint=((Coffre) o).findweapon();
                int ajoutindex=cofreint.size();
                while(cofreint.size()>0){
                    retour.put(cofreint.keySet().stream().findFirst().get()+index,indexglobal+
                            "."+cofreint.get(cofreint.keySet().stream().findFirst().get()));
                    cofreint.remove(cofreint.keySet().stream().findFirst().get());
                }
                index=index+ajoutindex;
            }
            indexglobal++;
        }
        return retour;
    }


    public LinkedHashMap<Integer,String> findpotion(){
        Integer index=0;
        int indexglobal = 0;
        LinkedHashMap<Integer,String> retour=new LinkedHashMap<>();
        for (Objet o: this.getContenu()) {
            if(o instanceof Potion){
                retour.put(index, String.valueOf(indexglobal));
                index++;
            }
            if(o instanceof Coffre){
                LinkedHashMap<Integer, String> cofreint=((Coffre) o).findweapon();
                int ajoutindex=cofreint.size();
                while(cofreint.size()>0){
                    retour.put(cofreint.keySet().stream().findFirst().get()+index,indexglobal+
                            "."+cofreint.get(cofreint.keySet().stream().findFirst().get()));
                    cofreint.remove(cofreint.keySet().stream().findFirst().get());
                }
                index=index+ajoutindex;
            }
            indexglobal++;
        }
        return retour;
    }



}
