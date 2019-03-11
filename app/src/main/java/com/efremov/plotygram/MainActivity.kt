package com.efremov.plotygram

import android.os.Bundle
import android.util.JsonReader
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.io.FileReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val REVIEW_TYPE = object : TypeToken<List<ChartData>>() {}.type
        val gson = Gson()
//        val reader = JsonReader(FileReader("app/chart_data.json"))
//        val data: List<ChartData> = gson.fromJson(reader, REVIEW_TYPE)
//        textview1.text = data.size.toString()

//        assets.open("app/chart_data.json").use { stream ->
//            try {
//                val data = gson.fromJson(InputStreamReader(stream), object : TypeToken<List<ChartData>>() {}.type) as List<ChartData>
//                textview1.text = data.size.toString()
//            } catch (t: Throwable) {
//                t.printStackTrace()
//            }
//        }

        RawAppData(this.assets, Gson())
            .getChartData()
            .onErrorReturn { emptyList() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { chartsData ->
                    textview1.text = chartsData.size.toString()
                },
                { error ->
                    error.printStackTrace()
                }
            )
            .connect()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun Disposable.connect() {
        compositeDisposable.add(this)
    }
}
