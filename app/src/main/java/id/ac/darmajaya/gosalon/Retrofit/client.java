package id.ac.darmajaya.gosalon.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class client {
    private  static  final  String BASE_URL = "http://pesonabdl.online/api/";

    public static Request getApi() {
        //Builder Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Request apiService = retrofit.create(Request.class);

        return apiService;
    }

}
