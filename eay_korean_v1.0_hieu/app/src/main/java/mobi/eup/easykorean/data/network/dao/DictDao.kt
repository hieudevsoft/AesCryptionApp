package mobi.eup.easykorean.data.network

import mobi.eup.easykorean.data.model.PostBodyDictWord
import mobi.eup.easykorean.data.network.response_examples.ResponseExample
import mobi.eup.easykorean.data.network.response_word.ResponseWord
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DictDao {
    @POST("/search")
    fun searchByWord(@Body postBodyDictWord: PostBodyDictWord):Call<ResponseWord>
    @POST("/search")
    fun searchExamples(@Body postBodyDictWord: PostBodyDictWord):Call<ResponseExample>
}