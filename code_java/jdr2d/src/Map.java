public class Map {
    private String nomLieu ;
    private char[][] carte;
    private int[] dimensions;

    protected Point position;

    //getters

    public String getNomLieu(){
        return nomLieu;
    }

    public char[][] getCarte(){
        return carte;
    }

    public int[] getDimensions(){
        return this.dimensions;
    }

    public Point getPosition() {
        return position;
    }

    //setters

    public Map setCarte(char[][] carte) {
        if(carte.length==dimensions[0] && carte[0].length==dimensions[1]) {
            this.carte = carte;
            return this;
        }
    else throw new IllegalArgumentException("Une carte ne peut pas dépasser ses dimensions");
    }

    public Map setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
        return this;
    }

    public Map setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public Map setPosition(Point p){
        this.position=p;
        return this;
    }

    //constructeurs

    public Map(){
        this.setDimensions(new int[]{1, 1}).setCarte(new char[][]{{'#'}}).setNomLieu("neant").setPosition(null);
    }

    public Map(int[] dimensions,char[][] carte,String nomLieu){
        this.setDimensions(dimensions).setCarte(carte).setNomLieu(nomLieu).setPosition(null);
    }

    public Map(int[] dimensions,char[][] carte,String nomLieu,Point p){
        this.setDimensions(dimensions).setCarte(carte).setNomLieu(nomLieu).setPosition(p);
    }

}
