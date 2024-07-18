public class Pacient {
    private long cnp;
    private String nume;
    private int varsta;
    private int cod;

    public Pacient(long cnp, String nume, int varsta, int cod) {
        this.cnp = cnp;
        this.nume = nume;
        this.varsta = varsta;
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

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "cnp=" + cnp +
                ", nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", cod=" + cod +
                '}';
    }
}
