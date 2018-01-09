package com.example.savchenko.recognizenotes.di;

import com.example.savchenko.recognizenotes.network.YandexService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Andrey on 06.10.2017.
 */
@Module()
class AppModule {
    private static final String BASE_URL = "asr.yandex.net";
    private static final String APP_KEY = "41a32ce6-731d-4af4-9cca-433f56aba378";

//
//
//    @Provides
//    @IntoMap
//    @ClassKey(MainActivity.class)
//    ComponentBuilder provideMain(MainComponent.Builder builder){
//        return builder;
//    }


    @Singleton
    @Provides
    YandexService mapService(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "audio/x-wav")
                            .addHeader("Transfer-Encoding", "chunked")
                            .build();
                    return chain.proceed(request);
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(YandexService.class);
    }


}
