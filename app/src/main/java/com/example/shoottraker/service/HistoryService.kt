package com.example.shoottraker.service

import com.example.shoottraker.dto.HistoryDto
import retrofit2.Call
import retrofit2.http.GET

interface HistoryService {
    @GET("/v3/c61c8d93-6504-4e3b-be8f-9d5c605f3573")
    fun listHistories(): Call<HistoryDto>
}