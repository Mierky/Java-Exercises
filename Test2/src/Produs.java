import java.io.Serializable;

public class Produs implements Serializable {
    private final String numeProdus;
    private final Categorie categorie;
    private final int pret;

    public Produs(String numeProdus, Categorie categorie, int pret) {
        this.numeProdus = numeProdus;
        this.categorie = categorie;
        this.pret = pret;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public int getPret() {
        return pret;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "numeProdus='" + numeProdus + '\'' +
                ", categorie=" + categorie +
                ", pret=" + pret +
                '}';
    }
}
