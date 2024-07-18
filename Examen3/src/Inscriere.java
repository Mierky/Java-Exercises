public class Inscriere {
    private long cnp;
    private String nume;
    private double notaBac;
    private int cod;

    public Inscriere(long cnp, String nume, double notaBac, int cod) {
        this.cnp = cnp;
        this.nume = nume;
        this.notaBac = notaBac;
        this.cod = cod;
    }

    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getNotaBac() {
        return notaBac;
    }

    public void setNotaBac(double notaBac) {
        this.notaBac = notaBac;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "Inscriere{" +
                "cnp=" + cnp +
                ", nume='" + nume + '\'' +
                ", notaBac=" + notaBac +
                ", cod=" + cod +
                '}';
    }
}
