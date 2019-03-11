package com.efremov.plotygram

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.io.InputStreamReader

class RawAppData(
    private val assets: AssetManager,
    private val gson: Gson)
{

    fun getChartData() : Single<List<ChartData>> = fromAssets/*<List<ChartData>>*/("app/chart_data.json")

    private /*inline*/ fun/* <reified T> */fromAssets(pathToAsset: String) = Single.defer {
        assets.open(pathToAsset).use { stream ->
            Single.just<List<ChartData>>(gson.fromJson(InputStreamReader(stream), object : TypeToken<List<ChartData>>() {}.type) as List<ChartData>)
        }
    }
}