package id.ac.darmajaya.gosalon.Retrofit;

import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.ResponTransaksi;
import id.ac.darmajaya.gosalon.Model.Konfirmasi.KonfrimasiSelesai;
import id.ac.darmajaya.gosalon.Model.Konfirmasi.ResponseSelesai;
import id.ac.darmajaya.gosalon.Model.Produk.ResponseProduk;
import id.ac.darmajaya.gosalon.Model.Toko.ResponseToko;
import id.ac.darmajaya.gosalon.Model.Transaksi.PostTransaksi;
import id.ac.darmajaya.gosalon.Model.User.ResponseLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Request {

    @GET("user/login.php")
    Call<ResponseLogin> getresponauth(@Query("email")String email, @Query("password") String password);


    @GET("transaksi/get.php")
    Call<ResponTransaksi> gettransaksi(@Query("id_user")String iduser);

    @POST("transaksi/user-selesai.php")
    Call<ResponseSelesai> konfrimasiselesai(@Body KonfrimasiSelesai konfrimasiSelesai);

    @POST("transaksi/post.php")
    Call<ResponTransaksi> posttransaksi(@Query("email")String email, @Query("password") String password, @Body PostTransaksi dataTransaksi);


    @FormUrlEncoded
    @POST("user/post.php")
    Call<ResponseLogin> getresponregistation
            (@Field("email") String email,
             @Field("password") String password,
             @Field("nama") String nama,
             @Field("alamat") String alamat,
             @Field("telp") String telp);


    @GET("toko/get.php")
    Call<ResponseToko> getdatatoko();


    @GET("produk/get.php")
    Call<ResponseProduk> getproduktoko(@Query("id_toko") String idtoko);


}
