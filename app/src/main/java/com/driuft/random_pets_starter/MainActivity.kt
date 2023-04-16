package com.driuft.random_pets_starter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var petList: MutableList<planetItem>
    private lateinit var rvPets: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPets = findViewById(R.id.pet_list)
        petList = mutableListOf()
        getDogImageURL()
    }
    private fun getDogImageURL() {
        val client = AsyncHttpClient()
//        val params = RequestParams()
//        params["api_key"] = "Yr3pyC5t1tIrm6cd73KePd8uE6UzPV0WO3CfrIxv"
//        params["count"] = "20"
        for (i in 0 until 20){
            val random  = Random.nextInt(1,1000)
//        client["https://dog.ceo/api/breeds/image/random/20", object : JsonHttpResponseHandler() {
//        client["https://api.nasa.gov/planetary/apod",params, object : JsonHttpResponseHandler() {
            client["https://pokeapi.co/api/v2/pokemon/$random", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog Success", "$json")
//                val petImageArray = json.jsonObject.getJSONArray("message")
//                for (i in 0 until petImageArray.length()) {
//                    petList.add(petImageArray.getString(i))
//                }

//                    val petImageArray = json.jsonArray.getJSONObject(i).getString("url")
//                    val petTitleArray = json.jsonArray.getJSONObject(i).getString("title")
//                    val petDateArray = json.jsonArray.getJSONObject(i).getString("date")
//                    petList.add(petImageArray,petTitleArray,petDateArray)
                    var newItem = planetItem(
//                        json.jsonArray.getJSONObject(i).getString("url"),
//                        json.jsonArray.getJSONObject(i).getString("title"),
//                        json.jsonArray.getJSONObject(i).getString("date")
                        json.jsonObject.getString("name"),
                        json.jsonObject.getString("id"),
                        json.jsonObject.getJSONObject("sprites").getString("front_default")
                    )
                    petList.add(newItem)


                Log.d("onSuccess" ,"$petList")
                val adapter = PetAdapter(petList)
                rvPets.adapter = adapter
                rvPets.layoutManager = LinearLayoutManager(this@MainActivity)

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
        }
    }
}