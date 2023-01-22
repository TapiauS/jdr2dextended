package tableau;

public class CarteDealer {

    public String toString(char[][] carte){
        StringBuffer retour=new StringBuffer();
        retour.append("{");
        for (int i = 0; i < carte.length; i++) {
            retour.append("{");
            for (int j = 0; j < carte[i].length; j++) {
                retour.append("''");
                retour.append(carte[i][j]);
                retour.append("''");
                if(j < carte[i].length-1) {
                    retour.append(",");
                }
            }
            retour.append("}");
            if(i<carte.length-1){
                retour.append(",");
            }
        }
        retour.append("}");
        return retour.toString();
    }

    public static char[][] carteParser(String carte){
        int index=0;
        int indexy=0;
        int indexx=0;
        int lengthx=0;
        int eos=carte.length();
        int lengthy=0;
        while(index<eos){
            while((carte.charAt(index)=='{' || carte.charAt(index)==','||carte.charAt(index)=='}')&&index<eos-1){
                index++;
            }
            while(carte.charAt(index)!='}'){

                while(carte.charAt(index)=='\''||carte.charAt(index)==','){
                    index++;
                }
                if(carte.charAt(index)!='}'){
                    lengthx++;
                    index++;
                }
            }
            index++;
            if(index+1<eos){
                lengthy++;
                lengthx=0;
            } else if (index<eos) {
                lengthy++;
            }
        }
        index=0;
        char[][] retour = new char[lengthy][lengthx];

        while(index<eos){
            while((carte.charAt(index)=='{' || carte.charAt(index)==','||carte.charAt(index)=='}')&&index<eos-1){
                index++;
            }
            while(carte.charAt(index)!='}'){
                while(carte.charAt(index)=='\''||carte.charAt(index)==','){
                    index++;
                }
                if(carte.charAt(index)!='}'){
                    retour[indexy][indexx]=carte.charAt(index);
                    indexx++;
                    index++;
                }
            }
            index++;
            if(index+1<eos){
                indexy++;
                indexx=0;
            } else if (index<eos) {
                indexy++;
            }
        }
        return retour;
    }
}
