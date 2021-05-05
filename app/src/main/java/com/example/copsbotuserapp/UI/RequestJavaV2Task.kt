package com.example.copsbotuserapp.UI

import android.app.Activity
import android.os.AsyncTask
import com.example.copsbotuserapp.MainActivity
import com.google.cloud.dialogflow.v2.*


class RequestJavaV2Task internal constructor(
    var activity: Activity,
    session: SessionName,
    sessionsClient: SessionsClient,
    queryInput: QueryInput
) :
    AsyncTask<Void?, Void?, DetectIntentResponse?>() {
    private val session: SessionName
    private val sessionsClient: SessionsClient
    private val queryInput: QueryInput
    override fun doInBackground(vararg params: Void?): DetectIntentResponse? {
        try {
            val detectIntentRequest: DetectIntentRequest = DetectIntentRequest.newBuilder()
                .setSession(session.toString())
                .setQueryInput(queryInput)
                .build()
            return sessionsClient.detectIntent(detectIntentRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(response: DetectIntentResponse?) {
        (activity as ChatActivity).callbackV2(response)
    }

    init {
        this.session = session
        this.sessionsClient = sessionsClient
        this.queryInput = queryInput
    }
}