package com.segnaore;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "giornata")
public class Giornata {

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "mattina")
    int mattina;
    @ColumnInfo(name = "pomeriggio")
    int pomeriggio;

    @ColumnInfo(name = "giorno")
    int giorno;
    @ColumnInfo(name = "mese")
    int mese;
    @ColumnInfo(name = "anno")
    int anno;
    @ColumnInfo(name = "ore")
    int ore_doppie;

    public Giornata(int giorno, int mese, int anno, int mattina, int pomeriggio){
        this.giorno=giorno;
        this.mese=mese;
        this.anno=anno;
        this.mattina=mattina;
        this.pomeriggio=pomeriggio;
        this.ore_doppie=mattina+pomeriggio;
    }

    public int getMattina() {
        return mattina;
    }

    public void setMattina(int mattina) {
        this.mattina = mattina;
    }

    public int getPomeriggio() {
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

    public int getOre_doppie() {
        return ore_doppie;
    }

    public void setOre_doppie(int ore_doppie) {
        this.ore_doppie = ore_doppie;
    }
}
