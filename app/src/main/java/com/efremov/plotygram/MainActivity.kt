package com.efremov.plotygram

import android.content.res.Configuration
import android.os.Bundle
import android.util.JsonReader
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.apply {
            inflateMenu(R.menu.menu_night_mode)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.nightModeAction -> {
                        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        } else if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }
                    }
                }
                true
            }
        }

//        val REVIEW_TYPE = object : TypeToken<List<ChartData>>() {}.type
//        val gson = Gson()
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
                    chartView.setChartData(chartsData[0])
                },
                { error ->
                    error.printStackTrace()
                }
            )
            .connect()

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels.toFloat()
        val screenWidth = displayMetrics.widthPixels.toFloat()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun Disposable.connect() {
        compositeDisposable.add(this)
    }
}
