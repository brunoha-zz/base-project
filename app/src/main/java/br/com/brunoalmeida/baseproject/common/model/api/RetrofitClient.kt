package br.com.brunoalmeida.baseproject.common.model.api


import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import br.com.brunoalmeida.baseproject.BuildConfig
import br.com.brunoalmeida.baseproject.common.util.Utils
import br.com.brunoalmeida.baseproject.common.constants.WebConstants
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by brunoalmeida on 15/03/18.
 */
open class RetrofitClient {

    private lateinit var retrofitClient: RetrofitClient


    private lateinit var sRetrofit: Retrofit
    private lateinit var sHttpClientBuilder: OkHttpClient.Builder


    fun <S> createService(serviceClass: Class<S>): S {

        sHttpClientBuilder = OkHttpClient.Builder().addInterceptor {

            chain: Interceptor.Chain ->
            val original = chain.request()

            if (!Utils.hasNetWork()) { //Verify Connection
                throw NoConnectivityException()
            }

            val url =
                    original
                            .url()
                            .newBuilder()
                            .addQueryParameter(WebConstants.API_KEY, WebConstants.API_KEY_VALUE)
                            .build()

            val requestBuilder = original.newBuilder()
                    .method(original.method(), original.body()).url(url)
            val request = requestBuilder.build()
            chain.proceed(request)

        }
        if (BuildConfig.DEBUG) {
            sHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().getSimpleLogging())
        }


        val client = sHttpClientBuilder
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
        sRetrofit = getClientBuilder(WebConstants.BASEURL).client(client).build()
        return sRetrofit.create(serviceClass)
    }

    fun getClientBuilder(baseUrl: String): Retrofit.Builder {

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
    }


}

fun HttpLoggingInterceptor.getSimpleLogging(): HttpLoggingInterceptor {

    val logging = this
    // logging: use NONE | HEADERS | BASIC | BODY to see request data
    logging.level = HttpLoggingInterceptor.Level.BODY

    return logging

}

fun downloadImage(context: Context, url: String?,
                  requestListener: RequestListener<Drawable>, imgView: ImageView) {
    Glide.with(context)
            .load(url)
            .listener(requestListener)
            .into(imgView)
}

class NoConnectivityException : IOException() {
    override val message: String?
        get() = "No connectivity exception"
}

