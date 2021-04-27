package com.example.android.politicalpreparedness.representative

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch
import java.lang.Exception

class RepresentativeViewModel(application: Application): AndroidViewModel(application) {

    //Done: Establish live data for representatives and address
    private val _representatives = MutableLiveData<List<Representative>>()
     val representatives: LiveData<List<Representative>>
         get() = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address


    //TODO: Create function to fetch representatives from API from a provided address
    fun getRepresentatives(address: String){
        viewModelScope.launch {
            try {

            val (offices, officials) = CivicsApi.retrofitService.getRepresentatives(address)
                _representatives.value = offices.flatMap { office ->
                    office.getRepresentatives(officials)
                }
            }
            catch (e: Exception){
                Log.i("Error Message", "Could not load Representatives")
            }
        }
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location
    fun setAddressFromGeoLocation(address: Address){
        _address.value = address

    }

    //TODO: Create function to get address from individual fields
    fun getAddressFromFields(address: Address){

    }

}
