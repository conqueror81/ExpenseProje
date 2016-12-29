package com.example.fatih.expenseproje;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListeleActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    TextView tvZaman;
    Button btGetir;
    ListView lvElemanlar;
    final ArrayList<Expense> elemanlar = new ArrayList<>();
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    final DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listele);





        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnTextClick();
        radioGroup = (RadioGroup) findViewById(R.id.radioGrup);
        lvElemanlar = (ListView) findViewById(R.id.lvProductList);


        btGetir = (Button) findViewById(R.id.btListele);
        btGetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Getirmece();
            }
        });
        registerForContextMenu(lvElemanlar);

    }
    public void Getirmece(){
        elemanlar.clear();
        final int tipi = radioGroup.getCheckedRadioButtonId();
        final List<Expense> expenses = db.getSpecificExpenses(tipi, month_x);

        for (Expense ex : expenses) {
            elemanlar.add(ex);
        }

        BindDictionary<Expense> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.pAy, new StringExtractor<Expense>() {
            @Override
            public String getStringValue(Expense item, int position) {
                return "" + item.getAy();
            }
        });
        dictionary.addStringField(R.id.pMiktar, new StringExtractor<Expense>() {
            @Override
            public String getStringValue(Expense item, int position) {
                return "" + item.getMiktar();
            }
        });
        dictionary.addStringField(R.id.pNot, new StringExtractor<Expense>() {
            @Override
            public String getStringValue(Expense item, int position) {
                return item.getNot();
            }
        });
        dictionary.addStringField(R.id.pTip, new StringExtractor<Expense>() {
            @Override
            public String getStringValue(Expense item, int position) {
                if (item.getTip()==2131427416){
                    return "GELİR";
                }else{
                    return "GİDER";
                }
                //return "" + item.getTip();
            }
        });
        FunDapter adapter = new FunDapter(ListeleActivity.this, elemanlar, R.layout.liste, dictionary);
        lvElemanlar.setAdapter(adapter);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Yapılacak İşlemi Seçiniz");
        menu.add(0, v.getId(), 0, "Düzenle");
        menu.add(0, v.getId(), 0, "Sil");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position=info.position;

        DatabaseHandler db=new DatabaseHandler(this);


        if(item.getTitle()=="Düzenle"){
            int elemanId=elemanlar.get(position).getId();
            Intent i = new Intent(getApplicationContext(),UpdateActivity.class);
            i.putExtra("ValueKey", elemanId+"");
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Düzenleme Ekranı",Toast.LENGTH_LONG).show();

        }
        else if(item.getTitle()=="Sil"){

            db.deleteExpense(elemanlar.get(position));
            Toast.makeText(getApplicationContext(),"Kayıt Silindi..",Toast.LENGTH_LONG).show();
            Getirmece();

        }else{
            return false;
        }
        return true;
    }

    public void showDialogOnTextClick() {
        tvZaman = (TextView) findViewById(R.id.tvZaman);
        tvZaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            tvZaman.setText(year_x + "/" + month_x + "/" + day_x);
        }
    };
}
