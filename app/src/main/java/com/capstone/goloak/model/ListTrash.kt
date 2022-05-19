package com.capstone.goloak.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListTrash(
    val id: String,
    val title: String,
    val description: String,
    val photoUrl: String,
    val totalPoint: Int
) : Parcelable