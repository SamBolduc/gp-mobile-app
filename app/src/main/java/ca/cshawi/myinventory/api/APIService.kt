package ca.cshawi.myinventory.api

import ca.cshawi.myinventory.boxes.Box
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface APIService {

    @GET("/boxes")
    fun getBoxes(): Call<List<Box>>

    @FormUrlEncoded
    @POST("/boxes/items")
    fun updateItems(@Field("data") body: String): Call<ActionResponse>

    @FormUrlEncoded
    @POST("/boxes/new")
    fun addBox(@Field("data") body: String): Call<ActionResponse>

    @FormUrlEncoded
    @POST
    fun addItem(@Url url: String, @Field("data") body: String): Call<ActionResponse>

    @DELETE
    fun delItem(@Url url: String): Call<ActionResponse>

    companion object {

        val INSTANCE = this.create()

        fun create(): APIService {
            val retrofit = Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl("https://gp.raphaeltheriault.com/").build()

            return retrofit.create(APIService::class.java)
        }
    }
}