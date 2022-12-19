public class Point {
    private int x;
    private int y;

    private Map lieux;

    //getters

    public int getX(){
        return x;
    }

    public  int getY(){
        return y;
    }

    //setters

    public void setX( int x){
        this.x=x;
    }

    public void setY( int y){
        this.y=y;
    }

    // methodes

    public int distance(Point p1){
        int x1=p1.getX();
        int y1=p1.getY();
        int dist;
        dist=Math.abs(x-x1)+Math.abs(y-y1);
        return dist;
    }

    public void depl(char dir){
        switch(dir){
            case 'E': this.x=this.x+1;
            case 'N': this.y=this.y+1;
            case 'O': this.x=this.x-1;
            case 'S': this.y=this.y-1;
            default: throw new IllegalArgumentException("Direction invalide!!!, choix possibles : E,N,S,O");
        }
    }
}
