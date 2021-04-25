package mobile.event_planner_app.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()

    fun setText(value:String){
        _text.postValue(value)
    }

    val text: LiveData<String> = _text
}