package mobi.eup.easykorean.data.network

import mobi.eup.easykorean.data.model.ItemFeed
import mobi.eup.easykorean.data.network.rest_support.postbody.PostNewsBody
import mobi.eup.easykorean.data.network.rest_support.postbody.PostItemNewsBody
import mobi.eup.easykorean.data.network.rest_support.postbody.PostSearchBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NewsRemoteDao {

    @POST("/api/news")
    fun getDataNews(@Body body: PostNewsBody): Call<MutableList<ItemFeed>>

    @POST("/api/news/search")
    fun getDataSearch(@Body body: PostSearchBody): Call<MutableList<ItemFeed>>

    @POST("/api/news/id")
    fun getItemBySever(@Body body: PostItemNewsBody): Call<ItemFeed>

}