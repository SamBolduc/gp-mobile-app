package ca.cshawi.myinventory.api

import ca.cshawi.myinventory.boxes.Box
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @GET("/boxes")
    fun getBoxes(): Call<List<Box>>

    @FormUrlEncoded
    @POST("/boxes/items")
    fun updateItems(@Field("data") body: String): Call<ActionResponse>

    companion object {

        val INSTANCE = this.create()

        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.168.2.17:3000/")
                .build()

            return retrofit.create(APIService::class.java)
        }
    }
}