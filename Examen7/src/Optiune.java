public class Optiune {
    private int cod_liceu;
    private int cod_specializare;

    public Optiune(int cod_liceu, int cod_specializare) {
        this.cod_liceu = cod_liceu;
        this.cod_specializare = cod_specializare;
    }

    public int getCod_liceu() {
        return cod_liceu;
    }

    public void setCod_liceu(int cod_liceu) {
        this.cod_liceu = cod_liceu;
    }

    public int getCod_specializare() {
        return cod_specializare;
    }

    public void setCod_specializare(int cod_specializare) {
        this.cod_specializare = cod_specializare;
    }

    @Override
    public String toString() {
        return "Optiune{" +
                "cod_liceu=" + cod_liceu +
                ", cod_specializare=" + cod_specializare +
                '}';
    }
}
