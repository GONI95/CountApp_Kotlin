package com.example.countapp_kotlin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.savedstate.SavedStateRegistry
import com.example.countapp_kotlin.databinding.ActivityMainBinding

// https://developer.android.com/topic/libraries/data-binding/start
// https://developer.android.com/topic/libraries/data-binding/expressions
// https://developer.android.com/kotlin/ktx#viewmodel
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    // viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    // 자바에선 이렇게 썼는데 코틀린에서는 위에 처럼 써야하는 건가보다.

    // kotlin에는 static 메소드가 없음, 패키지 수준에서 최상위 함수와 객체 선언을 사용 가능
    // 객체 선언은 최상위 함수가 접근할 수 없는 클래스 내에 private 멤버 변수에 접근해야 할 때 사용할 수 있다.
    // 상수로 사용할 데이터를 동반객체를 이용해 사용
    companion object{
        val TAG = MainActivity::class.java.simpleName
    }

    //var count_num = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        binding.lifecycleOwner = this
        // 12. livedata를 xml이 관찰하다가 변경하도록 할 수 있다.(새로고침)
        binding.viewModel = viewModel
        // 12. viewModel이 binding 객체에 설정된 xml로 전달됨, xml에 <data> 선언해줘야함

        /*
           viewModel.countLiveData.observe(this, Observer { count_num ->
            binding.countText.text = "$count_num"
        })
        // this: 해당 activity의 lifecycle에 맞게 동작함

         binding.btPlus.setOnClickListener { v->
            viewModel.increaseCount()
        }
        binding.btMinus.setOnClickListener { v->
            viewModel.decreaseCount()
        }
        // 이렇게 viewModel을 가져와도 0이 표시됨, xml에서 text를 0으로 설정해두어서 그렇지만 +,-누르면 정상값이 출력됨
         */

        Log.d(TAG, "onCreate : ")

        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            // Activity 활동에서 호출되는 lifecycle들이 콜백될 때 실행되는 메소드를 정의

            // 화면 전환 시 일시정지, 정지, 상태저장, 파괴, 생성, 시작, 재개 순으로 호출
            // 즉, onCreate가 다시 호출이 되니 당연히 데이터들이 초기화가 되는 것(화면 회전 시 count_num이 0이 되는 이유)
            // ViewModel로 해결 가능

            // onCreated 호출이 안되서 위에서 로그 찍음
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.d(TAG, "onActivityPaused : ")
            }

            override fun onActivityStarted(activity: Activity) {
                Log.d(TAG, "onActivityStarted : ")
            }

            override fun onActivityResumed(activity: Activity) {
                Log.d(TAG, "onActivityResumed : ")
            }

            override fun onActivityPaused(activity: Activity) {
                Log.d(TAG, "onActivityPaused : ")
            }

            override fun onActivityStopped(activity: Activity) {
                Log.d(TAG, "onActivityStopped : ")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.d(TAG, "onActivitySaveInstanceState : ")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.d(TAG, "onActivityDestroyed : ")
            }

        })
    }
    /*
    // 기존에 사용하던 onSaveInstanceState의 사용방법

        override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("count", viewModel.count_num)
        // outState에 저장하면 앱이 정지상태에서 꺼져도 값이 사라지지않음
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModel.count_num = savedInstanceState.getInt("count")
        // outState에서 저장된 정보를 가져오는 작업
    }
     */
}