public class Map {
    private String nomLieu ;
    private char[][] carte=new char[this.getDimensions()[0]][this.getDimensions()[1]];
    private int[] dimensions=new int[2];

    //getters

    public String getNomLieu(){
        return nomLieu;
    }

    public char[][] getCarte(){
        return carte;
    }

    public int[] getDimensions(){
        return dimensions;
    }

    //setters

    public Map setCarte(char[][] carte) {
        this.carte = carte;
        return this;
    }

    public Map setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
        return this;
    }

    public Map setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    //constructeurs

    public Map(){
        this.setDimensions(new int[]{1, 1}).setCarte(new char[][]{{'#'}}).setNomLieu("neant");
    }

    public Map(int[] dimensions,char[][] carte,String nomLieu){
        this.setDimensions(dimensions).setCarte(carte).setNomLieu(nomLieu);
    }

}
