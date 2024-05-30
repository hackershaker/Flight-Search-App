package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert fun insert(favorite: Favorite)

    @Delete fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorite") fun getAllFavorite(): Flow<List<Favorite>>

    @Query(
        "SELECT EXISTS(SELECT * FROM favorite WHERE departure_code = :departCode AND destination_code = :destinationCode)")
    fun isFavoriteExists(departCode: String, destinationCode: String): Flow<Boolean>

    @Query("SELECT * FROM favorite WHERE departure_code = :start AND destination_code = :end")
    fun getFavoriteByCode(start: String, end: String): Flow<Favorite>
}
