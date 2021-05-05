package com.example.newchatui.ui.chat

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.newchatui.R
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.android.synthetic.main.activity_description.botLogo
import java.util.*

class DescriptionActivity : AppCompatActivity() {

    var sharedName : String = "sharedPreference"
    var description:String = ""
    companion object {
        private const val REQUEST_CODE_STT = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)


        progress_bar_description.animateProgress(2000,40,50)
        Glide.with(this).asGif().load(R.raw.logo).into(botLogo)

        btnSpeek.setOnClickListener {
            val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            // Language model defines the purpose, there are special models for other use cases, like search.
            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            // Adding an extra language, you can use any language from the Locale class.
            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            // Text that shows up on the Speech input prompt.
            sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")
            try {
                // Start the intent for a result, and pass in our request code.
                startActivityForResult(sttIntent, REQUEST_CODE_STT)
            } catch (e: ActivityNotFoundException) {
                // Handling error when the service is not available.
                e.printStackTrace()
                Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
            }
        }

        btnDescription.setOnClickListener {
            description = edtDescription.text.toString()
            if(description != null){
                val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName,0)
                val editor = sharedPreferences.edit()
                editor.putString("description",description)
                editor.commit()
                var i = Intent(applicationContext, DateLocActivity::class.java)
                startActivity(i)
                overridePendingTransition(
                    R.anim.fade_in,
                    R.anim.fade_out
                )
            }

            else{
                Toast.makeText(applicationContext,"please enter the crime description",Toast.LENGTH_SHORT)
            }

        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Handle the result for our request code.
            REQUEST_CODE_STT -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        // Do what you want with the recognized text.
                        edtDescription.setText(recognizedText)
                    }
                }
            }
        }
    }
}
