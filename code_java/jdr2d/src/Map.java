public class Map {
    private String nomLieu ;
    private char[][] carte;
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


    public void setCarte(char[][] carte) {
        this.carte = carte;
    }

    public void setNomLieu(String nomLieu) {
        this.nomLieu = nomLieu;
    }

    public void setDimensions(int[] dimensions) {
        this.dimensions = dimensions;
    }

}
