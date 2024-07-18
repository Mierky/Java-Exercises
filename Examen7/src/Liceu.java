import java.util.List;

public class Liceu {
    private int cod_liceu;
    private String nume_liceu;
    private List<Specializare> specializari;

    public Liceu(int cod_liceu, String nume_liceu, List<Specializare> specializari) {
        this.cod_liceu = cod_liceu;
        this.nume_liceu = nume_liceu;
        this.specializari = specializari;
    }

    public int getCod_liceu() {
        return cod_liceu;
    }

    public void setCod_liceu(int cod_liceu) {
        this.cod_liceu = cod_liceu;
    }

    public String getNume_liceu() {
        return nume_liceu;
    }

    public void setNume_liceu(String nume_liceu) {
        this.nume_liceu = nume_liceu;
    }

    public List<Specializare> getSpecializari() {
        return specializari;
    }

    public void setSpecializari(List<Specializare> specializari) {
        this.specializari = specializari;
    }

    @Override
    public String toString() {
        return "Liceu{" +
                "cod_liceu=" + cod_liceu +
                ", nume_liceu='" + nume_liceu + '\'' +
                ", specializari=" + specializari +
                '}';
    }
}
