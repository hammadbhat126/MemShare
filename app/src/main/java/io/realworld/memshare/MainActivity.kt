package io.realworld.memshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.realworld.memshare.MySingleton.MySingleton.Companion.getInstance

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentImageUrl:String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }

   private  fun loadMeme (){
      progress.visibility = View.VISIBLE
// ...

// Instantiate the RequestQueue.

       val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
       val jsonObjectRequest = JsonObjectRequest(
           Request.Method.GET, url,null,
           Response.Listener { response ->
               currentImageUrl = response.getString("url")
               progress.visibility = View.INVISIBLE
               Glide.with(this).load(currentImageUrl).listener(object :RequestListener<Drawable>{

                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                    progress.visibility = View.GONE
                       return false
                   }

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progress.visibility = View.GONE
                       return false
                   }

               }).into(memeimageView
               )

           },
           Response.ErrorListener {


           })

// Add the request to the RequestQueue.
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


   }
    fun sharmeme(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, "ali is goofd  $currentImageUrl")
        val chooser = Intent.createChooser(intent, "share it to whatsapp")
        startActivity(chooser)



    }
    fun sharenext(view: View) {
 loadMeme()
    }
}