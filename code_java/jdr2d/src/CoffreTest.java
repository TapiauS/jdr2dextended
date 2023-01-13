import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoffreTest {
    private final Arme epee=new Arme("Epee",1,2,2,1);
    private final Arme epeelongue=new Arme("Epee longue",1,2,3,1);
    private final Coffre coffre1=new Coffre().add(epeelongue);
    private final Coffre coffre0=new Coffre().add(epee).add(coffre1);
    private final LinkedHashMap<Integer,String> result=new LinkedHashMap<>(){{
        put(0,"0");
        put(1,"1.0");
    }};
    @Test
    void findweapon() {
        System.out.println(coffre0.findweapon());
        assertEquals(result,coffre0.findweapon());
    }
}