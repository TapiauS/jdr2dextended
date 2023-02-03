package jdr2dcore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    int [] dimensiontest={5,5};
    private final Map testcity=new Map(dimensiontest,new char[5][5],"a",null,0);


    @Test
    void getDimensions() {
        assertEquals(5,testcity.getDimensions()[0]);
    }

    @Test
    void setDimensions(){
    }
}