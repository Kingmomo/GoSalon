package id.ac.darmajaya.gosalon.SPreferenced;

/**
 * Created by Ujang Wahyu on 04/01/2018.
 */

public class SPref {
    private static String id="id";
    private static String password="password";
    private static String nama="nama";
    private static String email="email";
    private static String alamat="alamat";
    private static String telp="telp";
    private static String userstatus = "userstatus";

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        SPref.id = id;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SPref.password = password;
    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        SPref.nama = nama;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SPref.email = email;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        SPref.alamat = alamat;
    }

    public static String getTelp() {
        return telp;
    }

    public static void setTelp(String telp) {
        SPref.telp = telp;
    }

    public static String getUserstatus() {
        return userstatus;
    }

    public static void setUserstatus(String userstatus) {
        SPref.userstatus = userstatus;
    }
}
