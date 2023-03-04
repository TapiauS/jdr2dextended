package jdr2dcore;

import java.io.Serializable;

public class Point implements Serializable {
    protected int x;
    protected int y;
    protected Map lieux;

    //getters

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Map getLieux() {
        return lieux;
    }

    //setters

    public Point setX(int x) {
        if (x < 0) {
            this.x = 0;
        } else if (x >= this.lieux.getCarte()[0].length) {
            this.x = this.lieux.getCarte()[0].length - 1;
        } else {
            this.x = x;
        }
        return this;
    }

    public Point setY(int y) {
        if (y < 0) {
            this.y = 0;
        } else if (y >= this.lieux.getCarte().length) {
            this.y = this.lieux.getCarte().length- 1;
        } else {
            this.y = y;
        }
        return this;
    }

    public Point setLieux(Map lieux) {
        this.lieux = lieux;
        return this;
    }

    //builders

    public Point() {
        this.setLieux(new Map()).setX(0).setY(0);
    }

    public Point(int x, int y, Map lieux) {

        this.setLieux(lieux).
                setY(y)
                .setX(x);
    }

    // methodes

    public int distance(Point p1) {
        if (p1.getLieux().getId() == this.getLieux().getId()) {
            int x1 = p1.getX();
            int y1 = p1.getY();
            int dist;
            dist = Math.abs(this.x - x1) + Math.abs(this.y - y1);
            return dist;
        } else {
            return 1000000000;
        }
    }

    public void depl(Direction dir) {
        switch (dir) {
            case EST -> {
                this.setX(this.getX() + 1);
                if (this.getLieux().getCarte()[this.getY()][this.getX()] == '#') {
                    this.depl(Direction.OUEST);
                    return;
                }
                return;
            }
            case NORD -> {
                this.setY(this.getY() - 1);
                if (this.getLieux().getCarte()[this.getY()][this.getX()] == '#') {
                    this.depl(Direction.SUD);
                    return;
                }
                return;
            }
            case OUEST -> {
                this.setX(this.getX() - 1);
                if (this.getLieux().getCarte()[this.getY()][this.getX()] == '#') {
                    this.depl(Direction.EST);
                    return;
                }
                return;
            }
            case SUD -> {
                this.setY(this.getY() + 1);
                if (this.getLieux().getCarte()[this.getY()][this.getX()] == '#') {
                    this.depl(Direction.NORD);
                    return;
                }
                return;
            }
        }
    }
}

