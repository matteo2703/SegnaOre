package com.segnaore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String[] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //ottenere la data di oggi
        TextView data = findViewById(R.id.data);
        String dataAttuale = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+" "+mesi[Calendar.getInstance().get(Calendar.MONTH)]+" "+Calendar.getInstance().get(Calendar.YEAR);
        data.setText(dataAttuale);

        //ottenere le ore del mese corrente
        AppDatabase db = AppDatabase.getInstance(this);
        ArrayList<Giornata> list = new ArrayList<>(db.giornataDao().getMese(Calendar.getInstance().get(Calendar.MONTH)));
        TextView totale = findViewById(R.id.totale);
        int oreTotali=0;
        for (int i=0;i<list.size();i++){
            oreTotali+=list.get(i).getOre_doppie();
        }
        boolean mezzaOra = oreTotali%2!=0;
        String tot = "Totale ore del mese: "+oreTotali/2;
        if (mezzaOra){
            tot+=(" e 1/2");
        }
        totale.setText(tot);

        //TODO impostare per inserire le ore della mattina e del pomeriggio
        //TODO aggiungere salvataggio e modifica
    }
}