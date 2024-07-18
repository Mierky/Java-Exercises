import java.io.Serializable;

public class Aventura implements Serializable {
    private int cod;
    private String denumire;
    private double tarif;
    private int locuriDisponibile;

    public Aventura(int cod, String denumire, double tarif, int locuriDisponibile) {
        this.cod = cod;
        this.denumire = denumire;
        this.tarif = tarif;
        this.locuriDisponibile = locuriDisponibile;
    }

    public int getCod() {
        return cod;
    }

    public String getDenumire() {
        return denumire;
    }

    public double getTarif() {
        return tarif;
    }

    public int getLocuriDisponibile() {
        return locuriDisponibile;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public void setLocuriDisponibile(int locuriDisponibile) {
        this.locuriDisponibile = locuriDisponibile;
    }

    @Override
    public String toString() {
        return "Activitate{" +
                "cod=" + cod +
                ", denumire='" + denumire + '\'' +
                ", tarif=" + tarif +
                ", locuriDisponibile=" + locuriDisponibile +
                '}';
    }
}
