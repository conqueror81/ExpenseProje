package com.example.fatih.expenseproje;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class KayitekleActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    EditText etMiktar,etNotu;
    TextView tvZaman;
    Button btEkle;

    int year_x,month_x,day_x;
    static final int DIALOG_ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitekle);
        final DatabaseHandler db=new DatabaseHandler(this);

        radioGroup=(RadioGroup)findViewById(R.id.radioGrup);
        etMiktar=(EditText)findViewById(R.id.etMiktar);
        etNotu=(EditText)findViewById(R.id.etNot);
        btEkle=(Button)findViewById(R.id.btEkleme);

        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnTextClick();

        btEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int tipi=radioGroup.getCheckedRadioButtonId();
                final String notu=etNotu.getText().toString();
                final int miktari=Integer.parseInt(etMiktar.getText().toString());
                Log.d("Insert: ","Inserting...");
                db.addExpense(new Expense(tipi,notu,miktari,day_x,month_x,year_x));
                Toast.makeText(getApplicationContext(),"Kayıt Eklendi",Toast.LENGTH_SHORT).show();
                etNotu.setText("");
            }
        });



    }

    public void showDialogOnTextClick(){
        tvZaman=(TextView)findViewById(R.id.tvZaman);
        tvZaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID){
            return  new DatePickerDialog(this,dpickerListener,year_x,month_x,day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            year_x=year;
            month_x=monthOfYear+1;
            day_x=dayOfMonth;
            tvZaman.setText(year_x+"/"+month_x+"/"+day_x);
        }
    };
}
