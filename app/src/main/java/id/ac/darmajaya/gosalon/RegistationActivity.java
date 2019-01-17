package id.ac.darmajaya.gosalon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Model.User.ResponseLogin;
import id.ac.darmajaya.gosalon.Retrofit.client;
import id.ac.darmajaya.gosalon.utils.Validate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistationActivity extends AppCompatActivity {

    private EditText nama, alamat, telp, email, password, password2;
    private ProgressDialog pDialog;
    private Context mContext;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        nama = (EditText) findViewById(R.id.nama);
        alamat = (EditText) findViewById(R.id.alamat);
        telp = (EditText) findViewById(R.id.telp);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        btn_register = (Button) findViewById(R.id.btn_register);
        mContext = this;


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasi())
                    register();
            }
        });

    }


    private void register() {
        pDialog = new ProgressDialog(this);
        // pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<ResponseLogin> register;
        register = client.getApi().getresponregistation(
                email.getText().toString().trim(),
                password.getText().toString(),
                nama.getText().toString(),
                alamat.getText().toString(),
                telp.getText().toString()

        );

        register.enqueue(new Callback<ResponseLogin>() {

            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                pDialog.hide();
                if (response.isSuccessful()) {
                    Toasty.success(mContext, "Berhasil dibuat", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                } else {
                    Toasty.error(mContext, "Gagal dibuat", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                pDialog.hide();
                Toasty.success(mContext, "Koneksi bermasalah", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Boolean validasi() {
        if (!Validate.cek(nama)
                && !Validate.cek(alamat)
                && !Validate.cek(telp)
                && !Validate.cek(email)
                && !Validate.cek(password)
                && !Validate.cek(password)
                && !Validate.cek(password2)) {
            if (Validate.cekPassword(password2, password.getText().toString(), password2.getText().toString())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
