package id.ac.darmajaya.gosalon.Model.Transaksi;

import com.google.gson.annotations.SerializedName;

public class ListProduk {
    @SerializedName("id_produk")
    private String idproduk;

    public ListProduk() {
    }

    public ListProduk(String idproduk) {
        this.idproduk = idproduk;
    }

    public String getIdproduk() {
        return idproduk;
    }

    public void setIdproduk(String idproduk) {
        this.idproduk = idproduk;
    }
}
