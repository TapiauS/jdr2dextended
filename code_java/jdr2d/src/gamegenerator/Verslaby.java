package gamegenerator;

import DAO.MapDAO;
import jdr2dcore.Map;

import java.sql.SQLException;
import java.util.Random;

public class Verslaby {

    private static int[] randomdepl(double[] p){
        Random randdir2 = new Random();
        int nextdir ;
        int[][] rotavers={{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int[] depl = new int[2];

        nextdir = randdir2.nextInt(100);
        if (nextdir <= p[0] * 100) {
            depl[0]=rotavers[0][0];
            depl[1]=rotavers[0][1];
        } else {
            if (p[0] * 100 < nextdir && nextdir <= (p[0] + p[1]) * 100) {
                depl[0] = rotavers[1][0];
                depl[1] = rotavers[1][1];
            } else {
                if ((p[0] + p[1]) * 100 < nextdir && nextdir <= (p[0] + p[1] + p[2]) * 100) {
                    depl[0]=rotavers[2][0];
                    depl[1]=rotavers[2][1];
                } else {
                    depl[0] = rotavers[3][0];
                    depl[1] = rotavers[3][1];
                }
            }
        }
        return depl;
    }
    private static char[][] petitvers(int x, int y, char[][] laby, int agemax) {
        Random randage = new Random();
        Random randdep = new Random();
        int nextdep; /*stocke le prochain déplacement du vers*/
        int agemaxvers = (int) (randage.nextInt(agemax) * 0.3 + agemax * 0.7); /* durée de vie maximale de notre vers*/
        int agevers = 0; /* age actuel de notre vers*/
        int xpvers = x;
        int ypvers = y;
        int[][] rotapvers = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        while (xpvers > 1 && xpvers < laby.length - 2 && ypvers > 1 && ypvers < laby[0].length - 2 && agevers < agemaxvers) {
            if (laby[xpvers + 1][ypvers] == ' ' && laby[xpvers - 1][ypvers] == ' ' && laby[xpvers][ypvers - 1] == ' ' && laby[xpvers][ypvers + 1] == ' ') {
                if(agevers>=agemaxvers*0.5){
                    laby[xpvers][ypvers] = 'C';
                }

                return laby; /* le vers meurt car aucun mur à portée, pauvre bête"*/
            }
            /* faisont bouger notre vers si il a à manger*/
            else {
                nextdep = randdep.nextInt(4);
                /* && laby[xpvers + rotapvers[nextdep][0]+rotapvers[nextdep][0]][ypvers + rotapvers[nextdep][1]+rotapvers[nextdep][1]] == ' '*/
                while ((laby[xpvers + rotapvers[nextdep][0]][ypvers + rotapvers[nextdep][1]] == ' ')) {
                    nextdep = randdep.nextInt(4);
                }
                xpvers = xpvers + rotapvers[nextdep][0];
                ypvers = ypvers + rotapvers[nextdep][1];
                agevers = agevers + 1;
                if(agevers==agemaxvers){
                    laby[xpvers][ypvers] = 'C';
                }
                else {
                    laby[xpvers][ypvers] = ' ';
                }
            }
        }
        return laby;

    }

    public static int[] trouvpos(char[][] laby,char valeur) {
        int[] pos;
        int x;
        int y;
        for (int i = 0; i < laby.length; i++) {

            for (int j = 0; j < laby[i].length; j++) {
                    /*System.out.println(laby[i][j]);
                    System.out.println(i);
                    System.out.println(j);*/
                if (laby[i][j] == valeur) {
                    /*System.out.println(new int[]{i, j});*/
                    x = i;
                    y = j;
                    pos= new int[]{x, y};
                    return pos;
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static char dir(int[] deplacement) {
        if (deplacement[0] == -1 & deplacement[1]==0) {
            return 'N';
        } else {
            if (deplacement[0] == 1 & deplacement[1]==0) {
                return 'S';
            } else {
                if (deplacement[1] == 1 & deplacement[0]==0) {
                    return 'E';
                } else {
                    if (deplacement[1] == -1 & deplacement[0]==0) {
                        return 'W';
                    }
                }
            }
        }
        return 'i';
    }
    public static char[][] construitlab(int l, int L,double g) { /*fonction qui crée un labyrinthe en utilisant la méthode des vers*/
        /*declaration de variable*/
        char[][] laby = new char[l][L];
        int compteur = -1; /* permet de positionner l'entrée et la sortie sur le bord*/
        Random randpv = new Random(); /* generation aleatoire d'un potit vers*/
        Random randE = new Random(); /*positionnement aléatoire de l'entrée)*/
        Random randS = new Random();
        int posS;
        int potitv;
        int xE; /*coordonnee x de E*/
        int yE; /*coordonnee y de E*/
        int xS; /*coordonnee x de S*/
        int yS; /*coordonnee y de S*/
        int xdep;
        int ydep;
        int xvers;
        int yvers;
        double[] pE = new double[4];
        double[] pS = new double[4];
        double[] p = new double[4];
        double distE;
        double distS;
        int[][] rotavers={{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int[] dpl ;
        char dirvers;/* direction du deplacement precedent du vers*/
        int tauxvide;
        int posE;

        /*debut du programme*/

        posE = randE.nextInt(2 * l + 2 * L - 8);/*le positionne au hasard sur le tour du labyrinthe en enlevant les coins*/
        do {
            posS=randS.nextInt(2 * l + 2 * L - 8);
        }while(posS==posE);
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < L; j++) {
                laby[i][j] = '#';
            }
        }

        /*4 boucles qui servent à positionner l'entrée*/
        for (int m = 1; m <= L - 2; m++) {
            compteur = compteur + 1;
            if (compteur == posE) {
                laby[0][m] = 'E';
            } else if (compteur == posS) {
                laby[0][m] = 'S';
            }
        }
        if (compteur < posE || compteur<posS ) {
            for (int m = 1; m <= l - 2; m++) {
                compteur = compteur + 1;
                if (compteur == posE) {
                    laby[m][L - 1] = 'E';
                }
                else if (compteur == posS) {
                    laby[m][l-1] = 'S';
                }
            }
        }
        if (compteur < posE || compteur<posS ) {
            for (int m = 1; m <= L - 2; m++) {
                compteur = compteur + 1;
                if (compteur == posE) {
                    laby[l - 1][L - 1 - m] = 'E';
                } else if (compteur == posS) {
                    laby[L-1][L-1-m] = 'S';
                }
            }
        }
        if (compteur < posE || compteur<posS) {
            for (int m = 1; m <= l - 2; m++) {
                compteur = compteur + 1;
                if (compteur == posE) {
                    laby[l - 1 - m][0] = 'E';
                } else if (compteur == posS) {
                    laby[l-1-m][0] = 'S';
                }
            }
        }
        xE = trouvpos(laby, 'E')[0];
        yE = trouvpos(laby, 'E')[1];
        xS=trouvpos(laby, 'S')[0];
        yS=trouvpos(laby, 'S')[1];
        /* trouvons la direction d'entrée de notre vers dans le labyrinthe*/
        if (yE == 0) {
            xdep = 0;
            ydep = 1;
        } else {
            if (xE == 0) {
                xdep = 1;
                ydep = 0;
            } else {
                if (yE == L - 1) {
                    xdep = 0;
                    ydep = -1;
                } else {
                    xdep = -1;
                    ydep = 0;
                }
            }
        }
        dirvers = dir(new int[]{xdep, ydep});
        xvers = xE + rot(dirvers)[1][0];
        yvers = yE + rot(dirvers)[1][1];
        laby[xvers][yvers] = ' ';
        /*boucle faisant traverser le labyrinthe a notre vers*/
        /*0 < xvers & yvers < (L - 1) & xvers < (l - 1) & yvers > 0*/
        while (xvers!=xS || yvers!=yS) {
            tauxvide=0;
            for (int i = 0; i < l-1; i++) {
                for (int j = 0; j < L-1; j++) {
                    if (laby[i][j] == ' ') {
                        tauxvide = tauxvide + 1;
                    }
                }
            }
            distE = Math.abs(xvers - xE) + Math.abs(yvers - yE);
            distS=Math.abs(xvers - xS) + Math.abs(yvers - yS);
            potitv = randpv.nextInt(4);
            /* on genere ici les proba de ce déplacer dans chaque direction en fonction de la distance a E*/
            if(tauxvide<(L*l)*g) {
                for (int i = 0; i < 4; i++) {
                    if (distE <= ((l + L) * 0.25)
                    ) {
                        if (xvers - xE == 0 || yvers - yE == 0) {
                            if ((Math.abs(xvers + rotavers[i][0] - xE) + Math.abs(yvers + rotavers[i][1] - yE) - distE) < 0) {
                                pE[i] = 0;
                            } else {
                                pE[i] = 1 / 3.0;
                            }
                        } else {
                            if ((Math.abs(xvers + rotavers[i][0] - xE) + Math.abs(yvers + rotavers[i][1] - yE) - distE) < 0) {
                                pE[i] = 0;
                            } else {
                                pE[i] = 1 / 2.0;
                            }
                        }
                    } else {
                        if (xvers - xE == 0 || yvers - yE == 0) {
                            if ((Math.abs(xvers + rotavers[i][0] - xE) + Math.abs(yvers + rotavers[i][1] - yE) - distE) < 0) {
                                pE[i] = 0.25 * 1 - 0.25 * 3 / distE;
                            } else {
                                pE[i] = 0.25 * 1 + 0.25 * 1 / distE;
                            }
                        } else {
                            if ((Math.abs(xvers + rotavers[i][0] - xE) + Math.abs(yvers + rotavers[i][1] - yE) - distE) < 0) {
                                pE[i] = 0.25 * (1 - 1 / distE);
                            } else {
                                pE[i] = 0.25 * (1 + 1 / distE);
                            }
                        }
                    }
                }
                for (int i = 0; i < 4; i++) {
                    if (distS <= ((l + L) * 0.25)
                    ) {
                        if (xvers - xS == 0 || yvers - yS == 0) {
                            if ((Math.abs(xvers + rotavers[i][0] - xS) + Math.abs(yvers + rotavers[i][1] - yS) - distS) < 0) {
                                pS[i] = 1;
                            } else {
                                pS[i] = 0;
                            }
                        } else {
                            if ((Math.abs(xvers + rotavers[i][0] - xS) + Math.abs(yvers + rotavers[i][1] - yS) - distS) < 0) {
                                pS[i] = 1 / 2.0;
                            } else {
                                pS[i] = 0;
                            }
                        }
                    } else {
                        if (xvers - xS == 0 || yvers - yS == 0) {
                            if ((Math.abs(xvers + rotavers[i][0] - xS) + Math.abs(yvers + rotavers[i][1] - yS) - distS) < 0) {
                                pS[i] = 0.25 * 1 + 0.25 * 3 / distS;
                            } else {
                                pS[i] = 0.25 * 1 - 0.25 * 1 / distS;
                            }
                        } else {
                            if ((Math.abs(xvers + rotavers[i][0] - xS) + Math.abs(yvers + rotavers[i][1] - yS) - distS) < 0) {
                                pS[i] = 0.25 * (1 + 1 / distS);
                            } else {
                                pS[i] = 0.25 * (1 - 1 / distS);
                            }
                        }
                    }
                    p[i] = (pE[i] + pS[i])*1/2.0;
                }
            }
            else{
                for (int i = 0; i < 4; i++) {
                   {
                        if (xvers - xS == 0 || yvers - yS == 0) {
                            if ((Math.abs(xvers + rotavers[i][0] - xS) + Math.abs(yvers + rotavers[i][1] - yS) - distS) < 0) {
                                p[i] = 1;
                            } else {
                                p[i] = 0;
                            }
                        } else {
                            if ((Math.abs(xvers + rotavers[i][0] - xS) + Math.abs(yvers + rotavers[i][1] - yS) - distS) < 0) {
                                p[i] = 0.5;
                            } else {
                                p[i] = 0;
                            }
                        }
                    }
                }
                System.err.println("J'essaie de sortir");
            }
            /*on deplace le vers suivant les probas calculées precedement*/
                do {
                    dpl = randomdepl(p);
                } while ((xvers + dpl[0] == 0 || xvers + dpl[0] == l - 1 || yvers + dpl[1] == 0 || yvers + dpl[1] == L - 1) && (yvers + dpl[1] != yS || (xvers + dpl[0]) != xS));


            xvers=xvers+dpl[0];
            yvers=yvers+dpl[1];
            if(laby[xvers][yvers]!='C') {
                laby[xvers][yvers] = ' ';
            }
            if (potitv >= 2 && xvers > 1 && xvers < l - 2 && yvers > 1 && yvers < L - 2) {
                laby = petitvers(xvers, yvers, laby, (L + l) / 2);
            }
        }
        laby[xvers][yvers] = 'S';
        return laby;
    }
    public static int[][] rot(char direction){
        int [] E={0,1};
        int [] N={-1,0};
        int [] W={0,-1};
        int [] S={1,0};
        int[][] retour ;
        if(direction=='E') {
            retour = new int[][]{N, E, S, W};
            return retour;
        }
        else {
            if (direction == 'N') {
                retour = new int[][]{W, N, E, S};
                return retour;
            } else {
                if (direction == 'W') {
                    retour = new int[][]{S, W, N, E};
                    return retour;
                } else {
                    if (direction == 'S') {
                        retour = new int[][]{E, S, W, N};
                        return retour;
                    }
                }
            }
        }
        /* cas ou la direction entrée n'a aucun sens : propose un déplacement impossible*/
        retour= new int[][]{{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};
        return retour;
    }

    public static char[][] construitgroslaby(int l, int L, double ratiovide) {
        int tauxvide = 0;
        char[][] laby = new char[l][L];
        double nbessai=0;
        double tauxreuss;
        while (tauxvide < (L * l) *ratiovide ) {
            tauxvide = 0;
            laby = construitlab(l, L, ratiovide);
            for (int i = 0; i < laby.length; i++) {
                for (int j = 0; j < laby[i].length; j++) {
                    if (laby[i][j] == ' ') {
                        tauxvide = tauxvide + 1;
                    }
                }
            }
            System.out.println(tauxvide);
            nbessai=nbessai+1;
            System.out.println(nbessai);
        }
        tauxreuss=1/nbessai;
        System.out.println(tauxreuss);
        return laby;
    }

   /* StringBuffer labytdraw=new StringBuffer();
        for (int i = 0; i < labyr.length; i++) {
        for (int j = 0; j < labyr[i].length; j++) {
            labytdraw.append(labyr[i][j]);
        }
        labytdraw.append('\n');
    }
    */

   /* public static void main (String[]args) throws SQLException {


        CarteDealer c=new CarteDealer();
        char [][] carte=Verslaby.construitgroslaby(80,80,0.6);
        Map m=new Map(new int[] {1,4},carte,"Caladon",9);
        m.setId(MapDAO.createMap(m));
        CoffreGenerator.filldatabase(m);
    }
    */

}
