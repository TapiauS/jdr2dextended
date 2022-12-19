public class Dialogue {
    private String contenuDialogue;
    private Dialogue[] dialoguePrecedent;
    private String[] choix;
    private boolean quete;

    //getters

    public String getContenuDialogue() {
        return contenuDialogue;
    }

    public Dialogue[] getDialoguePrecedent() {
        return dialoguePrecedent;
    }

    public String[] getChoix() {
        return choix;
    }

    public boolean isQuete() {
        return quete;
    }

    //setters

    public void setChoix(String[] choix) {
        this.choix = choix;
    }

    public void setContenuDialogue(String contenuDialogue) {
        this.contenuDialogue = contenuDialogue;
    }

    public void setDialoguePrecedent(Dialogue[] dialoguePrecedent) {
        this.dialoguePrecedent = dialoguePrecedent;
    }

    public void setQuete(boolean quete) {
        this.quete = quete;
    }


}
