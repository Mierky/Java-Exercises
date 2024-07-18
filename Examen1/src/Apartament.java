import java.io.Serializable;

public class Apartament implements Serializable {
    private int numarApartament;
    private int suprafata;
    private int numarPersoane;

    public Apartament(int numarApartament, int suprafata, int numarPersoane) {
        this.numarApartament = numarApartament;
        this.suprafata = suprafata;
        this.numarPersoane = numarPersoane;
    }

    public int getNumarApartament() {
        return numarApartament;
    }

    public void setNumarApartament(int numarApartament) {
        this.numarApartament = numarApartament;
    }

    public int getSuprafata() {
        return suprafata;
    }

    public void setSuprafata(int suprafata) {
        this.suprafata = suprafata;
    }

    public int getNumarPersoane() {
        return numarPersoane;
    }

    public void setNumarPersoane(int numarPersoane) {
        this.numarPersoane = numarPersoane;
    }

    @Override
    public String toString() {
        return "Apartament{" +
                "numarApartament=" + numarApartament +
                ", suprafata=" + suprafata +
                ", numarPersoane=" + numarPersoane +
                '}';
    }
}
