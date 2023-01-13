import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class Coffre extends Objet{
    protected ArrayList<Objet> contenu;
//getters

    public ArrayList<Objet> getContenu(){
        return contenu;
    }


    //setters

    public Coffre setContenu(ArrayList<Objet> contenu){
        this.contenu=contenu;
        return this;
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
        StringBuffer reste=new StringBuffer();
        while (indecxglobal.charAt(index)!='.' || index==indecxglobal.length()-1){
            reste.append(indecxglobal.charAt(index));
            index++;
        }
        if(index==indecxglobal.length()-1){
            int i=Integer.parseInt(String.valueOf(reste));
            Objet o=this.getContenu().get(i);
            this.remove(i);
            return o;
        }
        else{
            return findremove(String.valueOf(reste));
        }
    }

    public LinkedHashMap<Integer,String> findweapon(){
        Integer index=0;
        int indexglobal = 0;
        LinkedHashMap<Integer,String> retour=new LinkedHashMap<>();
        for (Objet o: this.getContenu()) {
            if(o instanceof Arme){
                System.out.println(index + " : " + o.getNomObjet() + " deg=" + ((Arme) o).getDeg() + " redudegat=" + ((Arme) o).getRedudeg() + " arme a " + ((Arme) o).getNbrmain() + " mains");
                retour.put(index, String.valueOf(indexglobal));
                index++;
            }
            if(o instanceof Coffre){
                LinkedHashMap<Integer, String> cofreint=((Coffre) o).findweapon();
                int ajoutindex=cofreint.size();
                while(cofreint.size()>0){
                    retour.put(cofreint.keySet().stream().findFirst().get()+index,indexglobal+"."+cofreint.get(cofreint.keySet().stream().findFirst().get()));
                    cofreint.remove(cofreint.keySet().stream().findFirst().get());
                }
                index=index+ajoutindex;
            }
            indexglobal++;
        }
        return retour;
    }



}
