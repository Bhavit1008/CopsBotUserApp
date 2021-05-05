package com.example.copsbotuserapp.UI

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.example.copsbotuserapp.API.RetrofitClient
import com.example.copsbotuserapp.R
import com.example.copsbotuserapp.models.DefaultResponse
import com.example.copsbotuserapp.models.LoginResponse
import com.google.api.gax.core.BackgroundResource
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.lang.Double.parseDouble


class ChatActivity : AppCompatActivity() {
    private var token: String = ""
    private var chat:String = ""
    private val uuid: String = java.util.UUID.randomUUID().toString()
    private var chatLayout: LinearLayout? = null
    private var queryEditText: EditText? = null
    var count = 0
    // Android client for older V1 --- recommend not to use this
//    private var aiRequest: AIRequest? = null
//    private var aiDataService: AIDataService? = null
//    private var customAIServiceContext: AIServiceContext? = null

    // Java V2
    private var sessionsClient: SessionsClient? = null
    private var session: SessionName? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val scrollview = findViewById<ScrollView>(R.id.chatScrollView)
        scrollview.post { scrollview.fullScroll(ScrollView.FOCUS_DOWN) }
        chatLayout = findViewById(R.id.chatLayout)
        val sendBtn: ImageView = findViewById(R.id.sendBtn)
        sendBtn.setOnClickListener { view: View -> sendMessage(view) }
        queryEditText = findViewById(R.id.queryEditText)
        queryEditText?.setOnKeyListener(View.OnKeyListener setOnKeyListener@{ view: View?, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                        sendMessage(sendBtn)
                        return@setOnKeyListener true
                    }
                    else -> {
                    }
                }
            }
            false
        })

        val preference=getSharedPreferences("myToken", Context.MODE_PRIVATE)
        token = preference.getString("access","no")
        Toast.makeText(applicationContext,token,Toast.LENGTH_SHORT).show()


        // Java V2
        initV2Chatbot()
    }

    private fun initV2Chatbot() {
        try {
            val stream: InputStream = resources.openRawResource(R.raw.auth)
            val credentials = GoogleCredentials.fromStream(stream)
            val projectId = (credentials as ServiceAccountCredentials).projectId
            val settingsBuilder: SessionsSettings.Builder = SessionsSettings.newBuilder()
            val sessionsSettings: SessionsSettings =
                settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build()
            sessionsClient = SessionsClient.create(sessionsSettings)
            session = SessionName.of(projectId, uuid)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendMessage(view: View) {
        val msg = queryEditText!!.text.toString()
        chat = chat + msg + "^#^"
        Log.d("request",chat)
        if (msg == "save"){
            sendChat()
        }





        if (msg.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(this, "Please enter your query!", Toast.LENGTH_LONG).show()
        } else {
            showTextView(msg, USER)
            queryEditText!!.setText("")
            // Android client for older V1 --- recommend not to use this
//            aiRequest.setQuery(msg);
//            RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
//            requestTask.execute(aiRequest);

            // Java V2
            val queryInput: QueryInput = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(msg).setLanguageCode("en-US")).build()
            session?.let { sessionsClient?.let { it1 ->
                RequestJavaV2Task(this, it,
                    it1, queryInput).execute()
            }
            }
        }




    }

//    fun callback(aiResponse: AIResponse?) {
//        if (aiResponse != null) {
//            // process aiResponse here
//            val botReply: String = aiResponse.getResult().getFulfillment().getSpeech()
//            Log.d(TAG, "Bot Reply: $botReply")
//            showTextView(botReply, BOT)
//        } else {
//            Log.d(TAG, "Bot Reply: Null")
//            showTextView(
//                "There was some communication issue. Please Try again!",
//                BOT
//            )
//        }
//    }

    fun callbackV2(response: DetectIntentResponse?) {
        if (response != null) {
            // process aiResponse here
            val botReply: String = response.getQueryResult().getFulfillmentText()
            Log.d(TAG, "V2 Bot Reply: $botReply")
            showTextView(botReply, BOT)
        } else {
            Log.d(TAG, "Bot Reply: Null")
            showTextView(
                "There was some communication issue. Please Try again!",
                BOT
            )
        }
    }

    private fun showTextView(message: String, type: Int) {
        val layout: FrameLayout
        layout = when (type) {
            USER -> userLayout
            BOT -> botLayout
            else -> botLayout
        }
        layout.isFocusableInTouchMode = true
        chatLayout!!.addView(layout) // move focus to text view to automatically make it scroll up if softfocus
        val tv = layout.findViewById<TextView>(R.id.chatMsg)
        tv.text = message
        layout.requestFocus()
        queryEditText!!.requestFocus() // change focus back to edit text to continue typing
    }

    private fun showTextView1(message: String, type: Int) {
        val layout: FrameLayout
        layout = when (type) {
            USER -> userLayout
            BOT -> botLayout
            else -> botLayout
        }
        layout.isFocusableInTouchMode = true
        chatLayout!!.addView(layout) // move focus to text view to automatically make it scroll up if softfocus
        val tv = layout.findViewById<TextView>(R.id.chatMsg)
        tv.text = message
        layout.requestFocus()
        queryEditText!!.requestFocus() // change focus back to edit text to continue typing
    }
    

    val userLayout: FrameLayout
        get() {
            val inflater = LayoutInflater.from(this)
            return inflater.inflate(R.layout.user_msg_layout, null) as FrameLayout
        }

    val botLayout: FrameLayout
        get() {
            val inflater = LayoutInflater.from(this)
            return inflater.inflate(R.layout.bot_msg_layout, null) as FrameLayout
        }

    companion object {
        private val TAG = ChatActivity::class.java.simpleName
        private const val USER = 10001
        private const val BOT = 10002
    }

    fun sendChat(){
        RetrofitClient.instance.saveChat(
            chat!!,
            token!!
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(applicationContext,"failed to save",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                Toast.makeText(applicationContext,"save ho gyi",Toast.LENGTH_SHORT).show()
            }

        })
    }
}