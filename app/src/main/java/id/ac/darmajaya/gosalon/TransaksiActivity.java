package id.ac.darmajaya.gosalon;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.ResponTransaksi;
import id.ac.darmajaya.gosalon.Model.Transaksi.DataTransaksi;
import id.ac.darmajaya.gosalon.Retrofit.client;
import id.ac.darmajaya.gosalon.SPreferenced.SPref;
import id.ac.darmajaya.gosalon.utils.Validate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiActivity extends AppCompatActivity {
    private CheckBox combobox;
    private EditText nama, alamat, notelp, paket, harga, koordinat, edDate, edClock;
    private Button btntranskasi;
    private DatePickerDialog mDatePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    TimePickerDialog timePickerDialog;
    String amPm;
    private ProgressBar progress_bar;
    private ProgressDialog pDialog;
    private Context mContext;
    private int MAP = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        combobox = (CheckBox) findViewById(R.id.combobox);
        nama = (EditText) findViewById(R.id.nama);
        alamat = (EditText) findViewById(R.id.alamat);
        notelp = (EditText) findViewById(R.id.notelp);
        paket = (EditText) findViewById(R.id.paket);
        harga = (EditText) findViewById(R.id.harga);
        edDate = (EditText) findViewById(R.id.date);
        koordinat = (EditText) findViewById(R.id.koordinat);
        edClock = (EditText) findViewById(R.id.clock);
        btntranskasi = (Button) findViewById(R.id.btntransaksi);

        mContext = this;
        final SharedPreferences spref = getApplicationContext().getSharedPreferences("TransaksiSalon", MODE_PRIVATE);
        paket.setText(spref.getString("namaproduk", null));
        harga.setText(spref.getString("harga", null));

        paket.setEnabled(false);
        harga.setEnabled(false);
        combobox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (combobox.isChecked()){
                    nama.setText(Prefs.getString(SPref.getNama(), null));
                    alamat.setText(Prefs.getString(SPref.getAlamat(), null));
                    notelp.setText(Prefs.getString(SPref.getTelp(), null));

                }
                else {
                    nama.setText("");
                    alamat.setText("");
                    notelp.setText("");
                }

            }
        });

        setDateTimeField();
        setwaktu();

        dismissKeyboard(edDate, this);
        edDate.setFocusable(false);
        edDate.clearFocus();
        dismissKeyboard(edClock, this);
        edClock.setFocusable(false);
        edClock.clearFocus();
        koordinat.clearFocus();
        dismissKeyboard(koordinat, this);
        koordinat.setFocusable(false);


        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog.show();

            }
        });
        edClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();

            }
        });
        btntranskasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate_login())
                posttransaksi();
            }
        });
        koordinat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivityForResult(intent, MAP);
            }
        });

    }

    private boolean validate_login() {
        return (!Validate.cek(nama)&&!Validate.cek(alamat)&&!Validate.cek(notelp)&&!Validate.cek(koordinat)&&!Validate.cek(edClock)&&!Validate.cek(edDate)) ? true : false;
    }


    public static void dismissKeyboard(EditText editText, Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void setwaktu(){
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
/*                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }*/
                edClock.setText(String.format("%02d:%02d", hourOfDay, minutes)/* + amPm*/);
            }
        }, currentHour, currentMinute, false);
    }


    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                edDate.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//            mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    public void posttransaksi(){
        pDialog = new ProgressDialog(this);
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();
        String tanggaldate;
        tanggaldate = edDate.getText().toString() + " " + edClock.getText().toString();

        final SharedPreferences spref = getApplicationContext().getSharedPreferences("TransaksiSalon", MODE_PRIVATE);


        DataTransaksi dataTransaksi = new DataTransaksi(
                Prefs.getString(SPref.getId(), null),
                spref.getString("idtoko", null),
                spref.getString("idproduk", null),
                spref.getString("idtoko", null),
                nama.getText().toString(),
                alamat.getText().toString(),
                notelp.getText().toString(),
                koordinat.getText().toString(),
                tanggaldate,
                "0"
        );

        Call<ResponTransaksi> user=client.getApi().posttransaksi(Prefs.getString(SPref.getEmail(), null), Prefs.getString(SPref.getPassword(), null), dataTransaksi);
        user.enqueue(new Callback<ResponTransaksi>() {
            @Override
            public void onResponse(Call<ResponTransaksi> call, Response<ResponTransaksi> response) {
                pDialog.hide();
                if (response.isSuccessful()){
                    System.out.println("Transaksi Sukses " + response.code());
                    Toasty.success(mContext,"Transaksi Berhasil",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(TransaksiActivity.this, MainActivity.class));
                    finish();



                }else{
                    Toasty.error(mContext,"Koneksi Tidak ada",Toast.LENGTH_LONG).show();
                    System.out.println("data user " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ResponTransaksi> call, Throwable t) {
                pDialog.hide();
                Toasty.error(mContext,"Koneksi Tidak ada",Toast.LENGTH_LONG).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
                System.out.println("data user" + t.getMessage());

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

       if(requestCode == MAP)
        {
   /*         double lat = (double) data.getExtras().get("location_lat");
            double lng = (double) data.getExtras().get("location_lng");
            System.out.println("mantap soul "+ lat +" "+ lng);*/
            if (resultCode == Activity.RESULT_OK)
            {
                double lat = (double) data.getDoubleExtra("location_lat", 0);
                double lng = (double) data.getDoubleExtra("location_lng", 0);
                System.out.println("mantap soul "+ lat +" "+ lng);
                koordinat.setText(String.valueOf(lat)+", "+String.valueOf(lng));
            }


        }
    }

}
