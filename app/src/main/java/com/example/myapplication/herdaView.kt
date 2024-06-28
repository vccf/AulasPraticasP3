package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class herdaView: ViewModel() {
    private var counter=0;
    private var counter2= MutableLiveData<Int>()
    fun addOne(){
        counter++

        //viewModelScope.launch{ //add dependencies
            //delay(1000)
            //counter2.value = counter //updates synchronously
            //counter2.postValue (counter) //updates asynchronously
        //}
    }
    fun getCounter(): String{
        //return counter.toString()
        return "im tired man ffs"
    }
}

//private var counter2=MutableLiveData<Int>
//fun addCOInter2.value=counter