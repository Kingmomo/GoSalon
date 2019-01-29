package id.ac.darmajaya.gosalon.Model.HistoryTransaksi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponTransaksi {

    private String message;
    @SerializedName("data")
    @Expose
    private List<GetTransaksi> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetTransaksi> getData() {
        return data;
    }

    public void setData(List<GetTransaksi> data) {
        this.data = data;
    }
}
