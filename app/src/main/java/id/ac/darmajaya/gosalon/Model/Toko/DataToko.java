package id.ac.darmajaya.gosalon.Model.Toko;

public class DataToko {

    private String id;
    private String id_admin;
    private String nama_toko;
    private String alamat_toko;
    private String pemilik;
    private String foto_toko;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }

    public String getNama_toko() {
        return nama_toko;
    }

    public void setNama_toko(String nama_toko) {
        this.nama_toko = nama_toko;
    }

    public String getAlamat_toko() {
        return alamat_toko;
    }

    public void setAlamat_toko(String alamat_toko) {
        this.alamat_toko = alamat_toko;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }

    public String getFoto_toko() {
        return foto_toko;
    }

    public void setFoto_toko(String foto_toko) {
        this.foto_toko = foto_toko;
    }
}
