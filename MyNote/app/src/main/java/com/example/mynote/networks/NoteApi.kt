package com.example.mynote.networks

import com.example.mynote.models.Note
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NoteApi {
    @GET("api/notes")
    suspend fun all(@Header("Authorization") token: String): ApiResponse<NoteListResponse>

    @GET("api/notes/{id}")
    suspend fun findById(@Header("Authorization") token: String,@Path("id") id: String): ApiResponse<NoteSingleResponse>

    @POST("api/notes")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Header("Authorization") token: String,@Body note: Note): ApiResponse<NoteSingleResponse>

    @PUT("api/notes/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Header("Authorization") token: String,@Path("id") id: String, @Body note: Note): ApiResponse<NoteSingleResponse>

    @DELETE("api/notes/{id}")
    suspend fun delete(@Header("Authorization") token: String,@Path("id") id: String): ApiResponse<DeleteResponse>

}