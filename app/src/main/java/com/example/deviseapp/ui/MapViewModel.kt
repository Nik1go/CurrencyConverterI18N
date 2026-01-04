package com.example.deviseapp.ui

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

data class ExchangeOffice(
    val id: String,
    val name: String,
    val address: String,
    val location: LatLng,
    val distance: Float? = null
)

class MapViewModel : ViewModel() {
    private val _userLocation = MutableLiveData<LatLng?>()
    val userLocation: LiveData<LatLng?> = _userLocation

    private val _exchangeOffices = MutableLiveData<List<ExchangeOffice>>()
    val exchangeOffices: LiveData<List<ExchangeOffice>> = _exchangeOffices

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _locationPermissionGranted = MutableLiveData<Boolean>()
    val locationPermissionGranted: LiveData<Boolean> = _locationPermissionGranted

    fun setUserLocation(location: LatLng) {
        _userLocation.value = location
    }

    fun setLocationPermissionGranted(granted: Boolean) {
        _locationPermissionGranted.value = granted
    }

    fun setExchangeOffices(offices: List<ExchangeOffice>) {
        _exchangeOffices.value = offices
    }

    fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

    fun setError(errorMessage: String?) {
        _error.value = errorMessage
    }

    fun calculateDistance(office: ExchangeOffice, userLocation: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            userLocation.latitude,
            userLocation.longitude,
            office.location.latitude,
            office.location.longitude,
            results
        )
        return results[0] / 1000f // Convert to kilometers
    }
}

