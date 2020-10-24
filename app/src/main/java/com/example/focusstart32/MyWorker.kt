package com.example.focusstart32


import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.math.BigInteger


class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val initNum = inputData.getInt("initNum", 0)

        val outputData = workDataOf("result" to factorial(initNum).toString())
        return Result.success(outputData)
    }

    private fun factorial(n: Int): BigInteger {
        var result: BigInteger = 1.toBigInteger()
        for (i in 1..n) {
            result = result * i.toBigInteger()
        }
        return result
    }
}