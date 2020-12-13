package com.example.countapp_kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(private val handle: SavedStateHandle) : ViewModel() {
    var countLiveData = MutableLiveData<Int>()
    private var count_num = handle.get<Int>("count") ?: 0
        // savedSateHandle : ViewModel을 이용해서 화면에 필요한 데이터를 안전히 보관하는 방법
        set(value) {
            println(value.toString())
            countLiveData.value = value
            field = value   // 작업을 마치고 값을 확정짓는다는 구문
            handle.set("count", value)
        }
    // 바뀌는 값이 value

    fun increaseCount() {
        count_num++
    }

    fun decreaseCount() {
        count_num--
    }
}


// 라이브데이터에다가도 savedstatehandle을 사용할 수 있지만 초기값이 없는 countLiveData는 눌 때문에 오류나니
// 복습 예제는 라이브데이터 하나로 써보자