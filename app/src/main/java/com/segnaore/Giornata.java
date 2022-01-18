package com.segnaore;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "giornata")
public class Giornata {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "mattina")
    double mattina;
    @ColumnInfo(name = "pomeriggio")
    double pomeriggio;

    @ColumnInfo(name = "giorno")
    int giorno;
    @ColumnInfo(name = "mese")
    int mese;
    @ColumnInfo(name = "anno")
    int anno;

    public Giornata(int giorno, int mese, int anno, double mattina, double pomeriggio){
        this.giorno=giorno;
        this.mese=mese;
        this.anno=anno;
        this.mattina=mattina;
        this.pomeriggio=pomeriggio;
    }

    public double getMattina() {
        return mattina;
    }

    public void setMattina(int mattina) {
        this.mattina = mattina;
    }

    public double getPomeriggio() {
        return pomeriggio;
    }

    public void setPomeriggio(int pomeriggio) {
        this.pomeriggio = pomeriggio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    public int getMese() {
        return mese;
    }

    public void setMese(int mese) {
        this.mese = mese;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }
}
