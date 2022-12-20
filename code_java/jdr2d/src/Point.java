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

        this.setY(y)
                .setX(x)
                .setLieux(lieux);
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
            case 'E': this.setX(this.getX()+1);
            case 'N': this.setY(this.getY()-1);
            case 'O': this.setX(this.getX()-1);
            case 'S': this.setY(this.getY()+1);
            default: throw new IllegalArgumentException("Direction invalide!!!, choix possibles : E,N,S,O");
        }
    }
}
