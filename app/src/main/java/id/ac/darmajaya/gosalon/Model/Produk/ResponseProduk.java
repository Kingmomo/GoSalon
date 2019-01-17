package id.ac.darmajaya.gosalon.Model.Produk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseProduk {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<DataProduk> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataProduk> getData() {
        return data;
    }

    public void setData(List<DataProduk> data) {
        this.data = data;
    }
}
