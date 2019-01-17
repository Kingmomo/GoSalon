package id.ac.darmajaya.gosalon.Model.Toko;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseToko {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<DataToko> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataToko> getData() {
        return data;
    }

    public void setData(List<DataToko> data) {
        this.data = data;
    }
}
