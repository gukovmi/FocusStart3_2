package com.example.focusstart32

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val workManager = WorkManager.getInstance(applicationContext)

        calculateButton.setOnClickListener {

            workManager.cancelAllWork()

            val initNum: Int = initNumEditText.text.toString().toInt()

            val myWorkRequest = OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(Data.Builder().putInt("initNum", initNum).build())
                .build()

            workManager.enqueue(myWorkRequest)

            workManager.getWorkInfoByIdLiveData(myWorkRequest.id)
                .observe(this, object : Observer<WorkInfo> {
                    override fun onChanged(workInfo: WorkInfo?) {
                        if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                            val result = workInfo.outputData.getString("result")
                            Log.e("result", result.toString())
                            resultTextView.visibility = View.VISIBLE
                            resultTextView.text = result
                        }
                    }
                })
        }
    }
}