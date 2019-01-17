package id.ac.darmajaya.gosalon.Model.HistoryTransaksi;

import java.util.List;

public class ResponTransaksi {

    private String message;
    private List<HistoryTransaksi> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HistoryTransaksi> getData() {
        return data;
    }

    public void setData(List<HistoryTransaksi> data) {
        this.data = data;
    }
}
