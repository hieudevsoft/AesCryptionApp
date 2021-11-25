package mobi.eup.easykorean.data.network
import retrofit2.Call
import retrofit2.http.*

interface AudioRemoteDao {
    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded; charset=UTF-8")
    @POST("/tomcat/servlet/vt")
    fun getSpeechLink(
        @Field("text") text: String,
        @Field("talkID") talkID: Int,
        @Field("volume") volume: Int,
        @Field("speed") speed: Int,
        @Field("pitch") pitch: Int,
        @Field("feeling") feeling: Int,
        @Field("dict") dict: Int
    ): Call<String>
}
