package com.example.savchenko.recognizenotes.network

import io.reactivex.Observable
import retrofit2.http.POST

/**
 * Created by savchenko on 09.01.18.
 */
interface YandexService {

    @POST
    fun recognizeAudio() : Observable<String>
}