package id.ac.darmajaya.gosalon.Model.HistoryTransaksi;

public class HistoryTransaksi {
    private String id;
    private String id_user;
    private String id_toko;
    private String id_produk;
    private String id_karyawan;
    private String nama;
    private String alamat;
    private String telp;
    private String kordinat;
    private String waktu_pemesanan;
    private String waktu_pengerjaan;
    private String foto_bukti;
    private String keterangan;
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getId_karyawan() {
        return id_karyawan;
    }

    public void setId_karyawan(String id_karyawan) {
        this.id_karyawan = id_karyawan;
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

    public String getWaktu_pengerjaan() {
        return waktu_pengerjaan;
    }

    public void setWaktu_pengerjaan(String waktu_pengerjaan) {
        this.waktu_pengerjaan = waktu_pengerjaan;
    }

    public String getFoto_bukti() {
        return foto_bukti;
    }

    public void setFoto_bukti(String foto_bukti) {
        this.foto_bukti = foto_bukti;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
