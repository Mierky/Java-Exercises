import java.util.List;

public class Candidat {
    private int cod_candidat;
    private String nume_candidat;
    private double media;
    private List<Optiune> optiuni;

    public Candidat(int cod_candidat, String nume_candidat, double media, List<Optiune> optiuni) {
        this.cod_candidat = cod_candidat;
        this.nume_candidat = nume_candidat;
        this.media = media;
        this.optiuni = optiuni;
    }

    public int getCod_candidat() {
        return cod_candidat;
    }

    public void setCod_candidat(int cod_candidat) {
        this.cod_candidat = cod_candidat;
    }

    public String getNume_candidat() {
        return nume_candidat;
    }

    public void setNume_candidat(String nume_candidat) {
        this.nume_candidat = nume_candidat;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public List<Optiune> getOptiuni() {
        return optiuni;
    }

    public void setOptiuni(List<Optiune> optiuni) {
        this.optiuni = optiuni;
    }

    @Override
    public String toString() {
        return "Candidat{" +
                "cod_candidat=" + cod_candidat +
                ", nume_candidat='" + nume_candidat + '\'' +
                ", media=" + media +
                ", optiuni=" + optiuni +
                '}';
    }
}
