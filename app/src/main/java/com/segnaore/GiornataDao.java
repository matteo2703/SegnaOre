package com.segnaore;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface GiornataDao {

    @Query("SELECT * FROM giornata WHERE mese = :mese AND anno = :anno")
    List<Giornata> getMese(int mese,int anno);

    @Query("SELECT * FROM giornata WHERE anno = :anno AND mese = :mese AND giorno = :giorno")
    List<Giornata> getGiornata(int anno,int mese,int giorno);

    @Query("UPDATE giornata SET mattina = :mattina, pomeriggio = :pomeriggio WHERE id = :id")
    int updateGioranta(double mattina, double pomeriggio, int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Giornata... giornatas);

    @Delete
    void delete(Giornata giornata);
}
