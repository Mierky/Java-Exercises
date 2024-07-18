public class Specializare {
    private int cod_specializare;
    private int numar_locuri;

    public Specializare(int cod_specializare, int numar_locuri) {
        this.cod_specializare = cod_specializare;
        this.numar_locuri = numar_locuri;
    }

    public int getCod_specializare() {
        return cod_specializare;
    }

    public void setCod_specializare(int cod_specializare) {
        this.cod_specializare = cod_specializare;
    }

    public int getNumar_locuri() {
        return numar_locuri;
    }

    public void setNumar_locuri(int numar_locuri) {
        this.numar_locuri = numar_locuri;
    }

    @Override
    public String toString() {
        return "Specializare{" +
                "cod_specializare=" + cod_specializare +
                ", numar_locuri=" + numar_locuri +
                '}';
    }
}
