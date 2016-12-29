package com.example.fatih.expenseproje;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    EditText etMiktar,etNote;
    Button btGuncelle;
    final DatabaseHandler db = new DatabaseHandler(this);
    Expense eleman;


    //Intent intent = getIntent();
    //final String myElemanId = intent.getExtras().getString("ValueKey");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);



        etMiktar=(EditText)findViewById(R.id.etMiktar);
        etNote=(EditText)findViewById(R.id.etNotem);
        btGuncelle=(Button)findViewById(R.id.btGuncelle);

        btGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               guncelle();
                startActivity(new Intent(UpdateActivity.this,ListeleActivity.class));
            }
        });
    }

public void guncelle(){
    String myElemanId="";
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
        myElemanId = extras.getString("ValueKey");
    }
    int miktari=Integer.parseInt(etMiktar.getText().toString());
    String notu=etNote.getText().toString();
    eleman=new Expense(miktari,notu,Integer.parseInt(myElemanId));
    db.updateContact(eleman);
}


}
