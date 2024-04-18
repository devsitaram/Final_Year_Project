package com.sitaram.foodshare.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * This is the interface RoomDao (Data Access Objects)
 * There have multiple function for room database call
 */
@SuppressWarnings("AndroidUnresolvedRoomSqlReference")
@Dao
interface RoomDao {

    // user profile
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: ProfileEntity)

    // Insert Food Details
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodDetails(foodsEntity: FoodsEntity)

    // history
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodHistory(history: HistoryEntity)

    // get user by id
    @Query("SELECT * FROM profile WHERE id=:userId")
    suspend fun getUserProfileById(userId: Int): ProfileEntity?

    // get foodDetails details by id
    @Query("SELECT * FROM foods WHERE id=:foodId")
    suspend fun getFoodDetailsById(foodId: Int?): FoodsEntity?

    // Get History By Id
    @Query("SELECT * FROM history WHERE id=:foodId")
    suspend fun getFoodHistoryById(foodId: Int): HistoryEntity?

    @Query("DELETE FROM profile WHERE email = :email")
    suspend fun deleteProfileByEmail(email: String): Int?

}