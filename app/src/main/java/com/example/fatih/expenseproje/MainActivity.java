package com.example.fatih.expenseproje;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Calendar;


public class MainActivity extends Activity {
    TextView tvBilgi;
    Button btKayit,btListele,btPieChart;
    int year_x,month_x;
    int s;
    final DatabaseHandler db=new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        Log.d("AY: ",month_x+"");


        tvBilgi=(TextView)findViewById(R.id.tvBilgi);

        btKayit=(Button)findViewById(R.id.btEklemeActivity);
        btKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,KayitekleActivity.class));
                AylikDurum();
            }
        });

        btListele=(Button)findViewById(R.id.btListeleActivity);
        btListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ListeleActivity.class));
                AylikDurum();
            }
        });

        btPieChart=(Button)findViewById(R.id.btPiechart);
        btPieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gelirler,giderler;
                gelirler=db.getAylikGelirToplam(month_x+1);
                giderler=db.getAylikGiderToplam(month_x+1);
                Intent intent = new Intent(MainActivity.this,PiechartActivity.class);
                intent.putExtra("GELIR", gelirler);
                intent.putExtra("GIDER",giderler);
                startActivity(intent);
            }
        });
        AylikDurum();





    }
    public void AylikDurum(){
        s = db.getDifferenceExpenses(month_x+1);
        if(s<0){
            tvBilgi.setTextColor(Color.RED);
        }
        tvBilgi.setText(""+s+"₺");
    }
}
