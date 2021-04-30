package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.OnConflictStrategy
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.Follow
import java.sql.RowId

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElection(election: Election)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElections(elections: List<Election>)

    //TODO: Add select all election query
    @Query("SELECT * FROM election_table")
    fun getAllElections(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE id in (SELECT id FROM follow_table)")
    fun getFollowedElections(): LiveData<List<Election>>

    //TODO: Add select single election query
    @Query("SELECT * FROM election_table WHERE id = :electionId")
    suspend fun getElectionId(electionId: Int) : Election?

    //TODO: Add delete query
    @Query("DELETE FROM election_table WHERE id = :electionId")
    suspend fun deleteElection(electionId: Int)

    //Done: Add clear query
    @Query("DELETE FROM election_table")
    suspend fun clearElection()


    //Follow Election::Class
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowed(follow: Follow)

    @Query("SELECT * FROM follow_table WHERE id = :id")
    fun getFollowed(id: Int): Int

    @Query("DELETE FROM follow_table WHERE id = :followedID")
    suspend fun deleteFollowed(followedID: Int)

    @Query("DELETE FROM follow_table")
    suspend fun clearFollowed()


}