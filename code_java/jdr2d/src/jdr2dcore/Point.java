package jdr2dcore;

public class Point {
    protected int x;
    protected int y;
    protected Map lieux;

    //getters

    public int getX(){
        return x;
    }

    public  int getY(){
        return y;
    }

    public Map getLieux() {
        return lieux;
    }

    //setters

    public Point setX( int x){
        if(x<0){
            this.x=0;
        }
        else if (x>=this.lieux.getDimensions()[0]) {
            this.x=this.lieux.getDimensions()[0]-1;
        }
        else{
            this.x=x;
        }
        return this;
    }

    public Point setY( int y){
        if(y<0){
            this.y=0;
        } else if (y>=this.lieux.getDimensions()[1]) {
            this.y=this.lieux.getDimensions()[1]-1;
        }
        else{
        this.y=y;
        }
        return this;
    }

    public Point setLieux(Map lieux) {
        this.lieux = lieux;
        return this;
    }

    //builders

    public Point(){
        this.setLieux(new Map()).setX(0).setY(0);
    }

    public Point(int x,int y, Map lieux){

        this.setLieux(lieux).
                setY(y)
                .setX(x);
    }

    // methodes

    public int distance(Point p1){
        if(p1.getLieux().getId()==this.getLieux().getId()) {
            int x1 = p1.getX();
            int y1 = p1.getY();
            int dist;
            dist = Math.abs(x - x1) + Math.abs(y - y1);
            return dist;
        }
        else {
            return 1000000000;
        }
    }

    public Point depl(char dir){
        switch (dir) {
            case 'E' -> {
                this.setX(this.getX() + 1);
                if (this.getLieux().getCarte()[this.getY()][this.getX()] == '#') {
                    this.depl('O');
                }
                return this;
            }
            case 'N' -> {
                this.setY(this.getY() - 1);
                if (this.getLieux().getCarte()[this.getY()][this.getX()] == '#') {
                    this.depl('S');
                }
                return this;
            }
            case 'O' -> {
                this.setX(this.getX() - 1);
                if (this.getLieux().getCarte()[this.getY()][this.getX()] == '#') {
                    this.depl('E');
                }
                return this;
            }
            case 'S' -> {
                this.setY(this.getY() + 1);
                if (this.getLieux().getCarte()[this.getY()][this.getX()] == '#') {
                    this.depl('N');
                }
                return this;
            }
            default -> throw new IllegalArgumentException("Direction invalide!!!, choix possibles : E,N,S,O");
        }
    }
}
