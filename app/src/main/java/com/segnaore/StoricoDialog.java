package com.segnaore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.util.ArrayList;
import java.util.Calendar;

public class StoricoDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Storico ore mensili")
                .setMessage(getOre())
                .setPositiveButton("OK", (dialog, which) -> dismiss());
        return builder.create();
    }

    public String getOre(){
        String[] mesi = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
        String ore="";
        int anno= Calendar.getInstance().get(Calendar.YEAR);

        ore+="Dicembre "+ (anno - 1) +": "+oreDelMese(mesi.length,anno-1);

        for (int i=0;i<mesi.length;i++){
            ore+="\n"+mesi[i]+" "+anno+": "+oreDelMese(i,anno);
        }

        return ore;
    }

    public String oreDelMese(int mese, int anno){
        final AppDatabase db = AppDatabase.getInstance(getActivity());
        ArrayList<Giornata> list = new ArrayList<>(db.giornataDao().getMese(mese,anno));
        double oreTotali=0;
        for (int i=0;i<list.size();i++){
            oreTotali+=list.get(i).getMattina()+list.get(i).getPomeriggio();
        }
        return String.valueOf(oreTotali);
    }
}
