import java.io.Serializable;

public class MesajServerClient implements Serializable {
    private String mesaj;
    private Object actiune;

    public MesajServerClient(String mesaj, Object actiune) {
        this.mesaj = mesaj;
        this.actiune = actiune;
    }
    public MesajServerClient(String mesaj) {
        this.mesaj = mesaj;
        this.actiune = null;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public Object getActiune() {
        return actiune;
    }

    public void setActiune(Object actiune) {
        this.actiune = actiune;
    }

    @Override
    public String toString() {
        return "MesajServerClient{" +
                "mesaj='" + mesaj + '\'' +
                ", actiune=" + actiune +
                '}';
    }
}
