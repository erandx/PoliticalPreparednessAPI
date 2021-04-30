package com.example.android.politicalpreparedness.election

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.database.ElectionDatabase.Companion.getDatabase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class VoterInfoViewModel(application: Application, private val election: Election) : AndroidViewModel(application) {

    private val electionDatabase = getDatabase(application)
    private val electionsRepository = ElectionsRepository(electionDatabase)

    private val TAG = VoterInfoViewModel::class.java.simpleName

    //Done: Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    //Done: Add var and methods to populate voter info

    suspend fun getVoterInfo() {
        val address = election.division.formatAddress()
        val electionId = election.id
        viewModelScope.launch {
            try {
                _voterInfo.value = CivicsApi.retrofitService.getVoterInfo(address, electionId)
            } catch (e: Exception) {
                Log.d(TAG, e.printStackTrace().toString())
            }
        }
    }

    //Done: Add var and methods to support loading URLs
    val urlIntent = MutableLiveData<String>()
    fun loadingUrl(uri: String?) {
        urlIntent.value = uri
    }

    //TODO: Add var and methods to save and remove elections to local database

    init {
        viewModelScope.launch {
            getVoterInfo()
            _followedElection.value = electionsRepository.isSaved(election)
        }
    }

    fun followElection() {
        viewModelScope.launch {
            if (_followedElection.value!!) {
                electionsRepository.unfollowElection(election)
            } else {
                electionsRepository.saveElection(election)
            }
            _followedElection.value = electionsRepository.isSaved(election)
        }
    }

    private val _followedElection = MutableLiveData<Boolean>(true)
    val followedElection: LiveData<Boolean>
        get() = _followedElection
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */
}