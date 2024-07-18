import java.io.Serializable;

public class Message implements Serializable {
    private String msj;
    private Object task;

    public Message(String msj, Object task) {
        this.msj = msj;
        this.task = task;
    }
    public Message(String msj) {
        this.msj = msj;
        this.task = null;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public Object getTask() {
        return task;
    }

    public void setTask(Object task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msj='" + msj + '\'' +
                ", task=" + task +
                '}';
    }
}
