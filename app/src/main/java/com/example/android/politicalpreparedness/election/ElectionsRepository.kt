package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.Follow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ElectionsRepository (private val database: ElectionDatabase) {

    val followedElections: LiveData<List<Election>> = database.electionDao.getFollowedElections()
    //list of Election to be displayed
    val elections: LiveData<List<Election>> = database.electionDao.getAllElections()

    suspend fun saveElection(election: Election){
        withContext(Dispatchers.IO){
            val follow = Follow(election.id)
            database.electionDao.insertFollowed(follow)
        }
    }
    suspend fun unfollowElection(election: Election){
        withContext(Dispatchers.IO){
            database.electionDao.deleteFollowed(election.id)
        }
    }

    suspend fun isSaved(election: Election): Boolean{
    var isSavedElection = false
        withContext(Dispatchers.IO){
            val result = database.electionDao.getFollowed(election.id)
            isSavedElection = result > 0
        }
        return isSavedElection
    }

    //Method to refresh the Offline Cache
    suspend fun refreshElections(){
        withContext(Dispatchers.IO){
            //We use try catch block to refresh Elections in offline mode
            try {
                val electionsList = CivicsApi.retrofitService.getElections()
                database.electionDao.insertElections(electionsList.elections)

            } catch (e: Exception){
                Log.i("Message", "Could not refresh Elections ${e.message}")
            }
        }
    }

}