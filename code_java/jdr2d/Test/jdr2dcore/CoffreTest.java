package jdr2dcore;

import jdr2dcore.Coffre;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class CoffreTest {
    private final Arme epee=new Arme("Epee",1,2,2,1);
    private final Arme epeelongue=new Arme("Epee longue",1,2,3,1);
    private final Arme epeelongue2=new Arme("Epee longue",1,2,3,1);
    private final Coffre coffre2=new Coffre().add(epeelongue2);
    private final Coffre coffre1= (Coffre) new Coffre().add(epeelongue).add(coffre2).setNomObjet("c2");
    private final Coffre coffre0= (Coffre) new Coffre().add(epee).add(coffre1).setNomObjet("c1");
    private final LinkedHashMap<Integer,String> result=new LinkedHashMap<>(){{
        put(0,"0");
        put(1,"1.0");
        put(2,"1.1.0");
    }};
    @Test
    void findweapon() {
        System.out.println(coffre0.findweapon());
        assertEquals(result,coffre0.findweapon());
    }

    @Test

    void find(){
        assertEquals(epeelongue2,coffre0.find("1.1.0"));
    }
}