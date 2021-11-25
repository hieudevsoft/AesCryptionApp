package mobi.eup.easykorean.data.network

import mobi.eup.easykorean.data.model.DatumContainer
import mobi.eup.easykorean.data.network.rest_support.postbody.PostTranslateBody
import mobi.eup.easykorean.data.network.rest_support.response.ReturnPostTranslate
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Bui Huy Hieu
 */
interface TranslateRemoteDao {

    @GET("/ko/api/news/{id}/{language}/{deviceID}")
    fun getTranslateData(
        @Path("id") id: String,
        @Path("language") vi: String,
        @Path("deviceID") deviceID: String,
    ): Call<DatumContainer>

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("/ko/api/addnews")
    fun postTranlateData(@Body body: PostTranslateBody): Call<ReturnPostTranslate>
}
