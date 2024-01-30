package com.nykaa.nykaacat.model


import android.os.Parcelable
import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Immutable
@Keep
@Parcelize
data class CatsItem(
    @SerializedName("height")
    val height: Int? = 0,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("width")
    val width: Int? = 0
) : Parcelable