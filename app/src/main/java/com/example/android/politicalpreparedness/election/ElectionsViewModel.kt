package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.network.models.Election

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application): ViewModel() {


    //TODO: Create live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    //TODO: Create live data val for saved elections
    private val _followedElections = MutableLiveData<List<Election>>()
    val followedElections: LiveData<List<Election>>
    get() = _followedElections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info
    private val _navigateToUpcomingElection = MutableLiveData<Election>()
    val navigateToUpcomingElection: LiveData<Election>
    get() = _navigateToUpcomingElection

    //Initiate navigation on Item Clicked
    fun displayElectionDetails(election: Election){
    _navigateToUpcomingElection.value = election
    }

    //We clean the LiveData to be triggered again after we return from Election Details
    fun displayElectionDetailsComplete(){
        _navigateToUpcomingElection.value = null
    }

}