package id.ac.darmajaya.gosalon.Model.HistoryTransaksi;

public class GetTransaksiProdukList {
    private String id;
    private String id_toko;
    private String nama_produk;
    private String harga_produk;
    private String waktu_pengerjaan;
    private String foto_produk;
    private String id_transaksi;
    private String id_produk;

    public GetTransaksiProdukList(String id, String id_toko, String nama_produk, String harga_produk, String waktu_pengerjaan, String foto_produk, String id_transaksi, String id_produk) {
        this.id = id;
        this.id_toko = id_toko;
        this.nama_produk = nama_produk;
        this.harga_produk = harga_produk;
        this.waktu_pengerjaan = waktu_pengerjaan;
        this.foto_produk = foto_produk;
        this.id_transaksi = id_transaksi;
        this.id_produk = id_produk;
    }

    public GetTransaksiProdukList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_toko() {
        return id_toko;
    }

    public void setId_toko(String id_toko) {
        this.id_toko = id_toko;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(String harga_produk) {
        this.harga_produk = harga_produk;
    }

    public String getWaktu_pengerjaan() {
        return waktu_pengerjaan;
    }

    public void setWaktu_pengerjaan(String waktu_pengerjaan) {
        this.waktu_pengerjaan = waktu_pengerjaan;
    }

    public String getFoto_produk() {
        return foto_produk;
    }

    public void setFoto_produk(String foto_produk) {
        this.foto_produk = foto_produk;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }
}
