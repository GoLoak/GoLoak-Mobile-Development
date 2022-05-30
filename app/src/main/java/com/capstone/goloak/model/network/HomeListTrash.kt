package com.capstone.goloak.model.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "home_list_trash")
@Parcelize
data class HomeListTrash(
    @PrimaryKey
    @field:SerializedName("id")
    var id: String,

    @field:SerializedName("name")
    var title: String,

    @field:SerializedName("description")
    var description: String,

    @field:SerializedName("photoUrl")
    var photoUrl: String
) : Parcelable
