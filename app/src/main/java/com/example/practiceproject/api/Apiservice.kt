package com.example.practiceproject.api

import com.example.practiceproject.UBikeData
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.http.*

interface Apiservice {
    @GET("/api/datasets/71CD1490-A2DF-4198-BEF1-318479775E8A/json")
    suspend fun getUBikeInfo():List<UBikeData>


    @GET("/api/datasets/71CD1490-A2DF-4198-BEF1-318479775E8A/json")
    suspend fun queryUBikeInfo(@Query("id")id:Int):List<UBikeData>
    //https://data.ntpc.gov.tw/api/datasets/71CD1490-A2DF-4198-BEF1-318479775E8A/json?id=2

    ///api/{path}}/71CD1490-A2DF-4198-BEF1-318479775E8A/json  value datasets
    ///api/datasets%7D/71CD1490-A2DF-4198-BEF1-318479775E8A/json

    ///api/{query}}/71CD1490-A2DF-4198-BEF1-318479775E8A/json  value datasets
    ///api/%7Bpath%7D%7D/71CD1490-A2DF-4198-BEF1-318479775E8A/json



    @POST("/api/datasets/71CD1490-A2DF-4198-BEF1-318479775E8A/json")
    suspend fun postUBikeInfo(@Body requestBody: RequestBody)
}

// model.toJson

//RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr)