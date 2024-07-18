public class Rezervare {
    private String id;
    private int codAventura;
    private int nrLocuri;

    public Rezervare(String id, int codAventura, int nrLocuri) {
        this.id = id;
        this.codAventura = codAventura;
        this.nrLocuri = nrLocuri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCodAventura() {
        return codAventura;
    }

    public void setCodAventura(int codAventura) {
        this.codAventura = codAventura;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "id='" + id + '\'' +
                ", codAventura=" + codAventura +
                ", nrLocuri=" + nrLocuri +
                '}';
    }
}
