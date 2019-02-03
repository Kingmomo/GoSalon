package id.ac.darmajaya.gosalon.Model.Konfirmasi;

public class KonfrimasiSelesai {
    private String id_transaksi;
    private String id_user;
    private String user_konfirm;

    public KonfrimasiSelesai(String id_transaksi, String id_user, String user_konfirm) {
        this.id_transaksi = id_transaksi;
        this.id_user = id_user;
        this.user_konfirm = user_konfirm;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUser_konfirm() {
        return user_konfirm;
    }

    public void setUser_konfirm(String user_konfirm) {
        this.user_konfirm = user_konfirm;
    }
}
