package com.example.turfnow.database.apiservice.faq

import com.google.gson.annotations.SerializedName

data class Faq(
@SerializedName("faq_id")
val id: Long = 0,
@SerializedName("ques")
val ques: String = "",
@SerializedName("ans")
val ans: String="")
