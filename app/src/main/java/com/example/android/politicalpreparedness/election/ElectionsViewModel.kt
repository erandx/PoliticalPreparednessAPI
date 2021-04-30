package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch
import java.lang.Exception

class ElectionsViewModel(application: Application) : AndroidViewModel(application) {

    private val electionDatabase = ElectionDatabase.getDatabase(application)
    private val electionsRepository = ElectionsRepository(electionDatabase)

    //Done: Create live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = electionsRepository.elections

    //Done: Create live data val for saved elections
    private val _followedElections = MutableLiveData<List<Election>>()
    val followedElections: LiveData<List<Election>>
        get() = electionsRepository.followedElections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    init {
        viewModelScope.launch {
            electionsRepository.refreshElections()
            getElections()
        }
    }

    fun getElections() {
        viewModelScope.launch {
            try {
                _upcomingElections.value = CivicsApi.retrofitService.getElections().elections
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //DONE: Create functions to navigate to saved or upcoming election voter info
    private val _navigateToUpcomingElection = MutableLiveData<Election?>()
    val navigateToUpcomingElection: LiveData<Election?>
        get() = _navigateToUpcomingElection

    //Initiate navigation on Item Clicked
    fun displayElectionDetails(election: Election) {
        _navigateToUpcomingElection.value = election
    }

    //We clean the LiveData to be triggered again after we return from Election Details
    fun displayElectionDetailsComplete() {
        _navigateToUpcomingElection.value = null
    }

}