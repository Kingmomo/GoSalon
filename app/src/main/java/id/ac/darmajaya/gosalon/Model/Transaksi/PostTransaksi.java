package id.ac.darmajaya.gosalon.Model.Transaksi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostTransaksi {
    @SerializedName("id")
    private String idtransaksi;
    private String id_user;
    private String id_toko;
    @SerializedName("produk")
    private List<ListProduk> listproduk;
    private String nama;
    private String alamat;
    private String telp;
    private String kordinat;
    private String waktu_pemesanan;
    private String status;

    public PostTransaksi(String idtransaksi, String id_user, String id_toko, List<ListProduk> listproduk, String nama, String alamat, String telp, String kordinat, String waktu_pemesanan, String status) {
        this.idtransaksi = idtransaksi;
        this.id_user = id_user;
        this.id_toko = id_toko;
        this.listproduk = listproduk;
        this.nama = nama;
        this.alamat = alamat;
        this.telp = telp;
        this.kordinat = kordinat;
        this.waktu_pemesanan = waktu_pemesanan;
        this.status = status;
    }

    public String getIdtransaksi() {
        return idtransaksi;
    }

    public void setIdtransaksi(String idtransaksi) {
        this.idtransaksi = idtransaksi;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_toko() {
        return id_toko;
    }

    public void setId_toko(String id_toko) {
        this.id_toko = id_toko;
    }

    public List<ListProduk> getListproduk() {
        return listproduk;
    }

    public void setListproduk(List<ListProduk> listproduk) {
        this.listproduk = listproduk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getKordinat() {
        return kordinat;
    }

    public void setKordinat(String kordinat) {
        this.kordinat = kordinat;
    }

    public String getWaktu_pemesanan() {
        return waktu_pemesanan;
    }

    public void setWaktu_pemesanan(String waktu_pemesanan) {
        this.waktu_pemesanan = waktu_pemesanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
