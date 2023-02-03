package jdr2dcore;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    private char[][] maptest= {{'a','a','a','a','a'},{'a','a','a','a','a'},{'a','a','a','a','a'},{'a','a','a','a','a'},{'a','a','a','a','a'}};
    private Map testcity=new Map(new int[]{5, 5},new char[5][5],"testcity",0);
    private Point pointestobvious=new Point(2,3,testcity);
    private Point pointtestor=new Point(0,0,testcity);
    private final Point pointref=new Point(4,4,testcity);

    @Test
    void setX() {
    }

    @Test
    void setY() {
    }

    @Test
    void distance() {
        assertEquals(5,pointref.distance(pointtestor));
    }

    @Test
    void depl() {
        assertEquals(4,pointref.depl('E').getX());
    }
}