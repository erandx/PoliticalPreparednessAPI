package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class VoterInfoViewModel(application: Application) : ViewModel() {

    //Done: Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    val selectedElection = MutableLiveData<Election>()

    private val _electionId = MutableLiveData<Int>()
    val electionId: LiveData<Int>
    get() = _electionId

    //Done: Add var and methods to populate voter info
    fun getVoterInfo(address: String, electionId: Int){
        viewModelScope.launch {
            try{
                _voterInfo.value = CivicsApi.retrofitService.getVoterInfo(address, electionId)
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun getElection(id: Int){
    _electionId.value = id
    }

    //Done: Add var and methods to support loading URLs
     val urlIntent = MutableLiveData<String>()
    fun loadingUrl (uri: String?){
        urlIntent.value = uri
    }

    //TODO: Add var and methods to save and remove elections to local database
    fun followElection(election: Election){

    }
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}