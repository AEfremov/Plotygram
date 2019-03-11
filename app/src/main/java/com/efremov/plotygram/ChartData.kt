package com.efremov.plotygram

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChartData(
    @SerializedName("columns") val columns: List<List<String>>,
    @SerializedName("types") val types: AxisType,
    @SerializedName("names") val names: AxisName,
    @SerializedName("colors") val colors: AxisColor
)

data class ColumnData(
    val columnValue: List<String>
)

data class AxisType(
    @SerializedName("y0") val y0: String,
    @SerializedName("y1") val y1: String,
    @SerializedName("x") val x: String
)

data class AxisName(
    @SerializedName("y0") val y0: String,
    @SerializedName("y1") val y1: String
)

data class AxisColor(
    @SerializedName("y0") val y0: String,
    @SerializedName("y1") val y1: String
)
