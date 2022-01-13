package com.segnaore;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String[] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
    private String matt, pome;
    private double oreMattina, orePomeriggio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //ottenere la data di oggi
        final TextView data = findViewById(R.id.data);
        String dataAttuale = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+" "+mesi[Calendar.getInstance().get(Calendar.MONTH)]+" "+Calendar.getInstance().get(Calendar.YEAR);
        data.setText(dataAttuale);

        //ottenere le ore del mese corrente
        final AppDatabase db = AppDatabase.getInstance(this);
        ArrayList<Giornata> list = new ArrayList<>(db.giornataDao().getMese(Calendar.getInstance().get(Calendar.MONTH)));
        final TextView totale = findViewById(R.id.totale);
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

        //inserire ore per mattina e pomeriggio
        //inizializzazione componenti
        final AutoCompleteTextView mattina, pomeriggio;
        mattina = findViewById(R.id.mattina);
        pomeriggio = findViewById(R.id.pomeriggio);
        mattina.setInputType(InputType.TYPE_NULL);
        pomeriggio.setInputType(InputType.TYPE_NULL);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.ore));
        mattina.setAdapter(arrayAdapter);
        pomeriggio.setAdapter(arrayAdapter);
        mattina.setOnFocusChangeListener((v, hasFocus) ->mattina.showDropDown());
        mattina.setOnClickListener(v -> {
            mattina.showDropDown();
            hideSoftKeyboard(this);
        });
        mattina.setOnItemClickListener((parent, view, position, id) ->matt = (String) parent.getItemAtPosition(position));
        pomeriggio.setOnFocusChangeListener((v, hasFocus) ->pomeriggio.showDropDown());
        pomeriggio.setOnClickListener(v -> {
            pomeriggio.showDropDown();
            hideSoftKeyboard(this);
        });
        pomeriggio.setOnItemClickListener((parent, view, position, id) ->pome = (String) parent.getItemAtPosition(position));

        final Button salva=findViewById(R.id.salva);
        //TODO salvare le ore nel giorno stabilito e permettere la modifica
        salva.setOnClickListener(v -> {
            if (matt!=null){
                oreMattina = Double.parseDouble(matt);
            }
            if (pome!=null){
                orePomeriggio=Double.parseDouble(pome);
            }

            Toast.makeText(this, "Ore totali di oggi: "+(oreMattina+orePomeriggio), Toast.LENGTH_SHORT).show();
        });
    }

    public void hideSoftKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view==null){
            view=new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}