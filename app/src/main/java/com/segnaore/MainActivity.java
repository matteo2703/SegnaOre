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
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
    private String matt, pome;
    private int anno, mese, giorno = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        final AppDatabase db = AppDatabase.getInstance(this);

        //ottenere la data di oggi
        final TextView data = findViewById(R.id.data);
        anno=Calendar.getInstance().get(Calendar.YEAR);
        mese=Calendar.getInstance().get(Calendar.MONTH);
        giorno=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String dataAttuale = giorno+" "+mesi[mese]+" "+anno;
        data.setText(dataAttuale);

        //cambiare gioranta
        data.setOnClickListener(v -> {
            //TODO selezionare goirno e renderlo il giorno corrente
        });

        //ottenere le ore del mese corrente
        final TextView totale = findViewById(R.id.totale);
        totale.setText(oreDelMese(mese,anno));

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

        if(db.giornataDao().getGiornata(anno,mese,giorno).size()!=0){
            List<Giornata> giornata = db.giornataDao().getGiornata(anno,mese,giorno);
            mattina.setText(getOrario(giornata.get(0),0));
            pomeriggio.setText(getOrario(giornata.get(0),1));
        }

        final Button salva=findViewById(R.id.salva);
        ///controllo se nel giorno corrente erano già state salvate delle ore
        salva.setOnClickListener(v -> {

            if(db.giornataDao().getGiornata(anno,mese,giorno).size()!=0){
                List<Giornata> giornata = db.giornataDao().getGiornata(anno,mese,giorno);
                db.giornataDao().updateGioranta(Double.parseDouble(matt),Double.parseDouble(pome),giornata.get(0).getId());
            }else{
                Giornata giornata = new Giornata(giorno,mese,anno,Double.parseDouble(matt),Double.parseDouble(pome));
                db.giornataDao().insertAll(giornata);
            }
            totale.setText(oreDelMese(mese,anno));
        });

        final ImageButton storico = findViewById(R.id.storico);
        storico.setOnClickListener(v -> {
            //TODO visualizzare lo storico dell'anno + dicembre dell'anno precedente
            openStorico();
        });
    }

    public String getOrario(Giornata giornata, int parteDellaGiornata){
        //parte della gioranta: 0=mattina, 1=pomeriggio
        String[] ore = getResources().getStringArray(R.array.ore);
        for (String s : ore) {
            if (parteDellaGiornata == 0) {
                if (giornata.getMattina() == Double.parseDouble(s)) {
                    return s;
                }
            } else {
                if (giornata.getPomeriggio() == Double.parseDouble(s)) {
                    return s;
                }
            }
        }
        return null;
    }

    public String oreDelMese(int mese,int anno){
        final AppDatabase db = AppDatabase.getInstance(this);
        ArrayList<Giornata> list = new ArrayList<>(db.giornataDao().getMese(mese,anno));
        double oreTotali=0;
        for (int i=0;i<list.size();i++){
            oreTotali+=list.get(i).getMattina()+list.get(i).getPomeriggio();
        }
        return "Totale ore del mese: "+oreTotali;
    }

    public void openStorico(){
        StoricoDialog dialog = new StoricoDialog();
        dialog.show(getSupportFragmentManager(),"");
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