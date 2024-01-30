package com.nykaa.nykaacat.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.compose.runtime.Immutable

@Immutable
@Keep
@Parcelize
data class CatsItem(
    @SerializedName("height")
    val height: Int? = 0,
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("width")
    val width: Int? = 0
) : Parcelable