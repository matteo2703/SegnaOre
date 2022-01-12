package com.segnaore;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface GiornataDao {

    @Query("SELECT * FROM giornata WHERE mese = :mese")
    List<Giornata> getMese(int mese);

    @Query("UPDATE giornata SET mattina = :mattina, pomeriggio = :pomeriggio WHERE id = :id")
    int updateGioranta(int mattina, int pomeriggio, int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Giornata... giornatas);

    @Delete
    void delete(Giornata giornata);
}
