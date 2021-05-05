package com.example.newchatui.ui.chat.awarness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.newchatui.R;
import com.example.newchatui.dialogFlowHelper.RequestJavaV2Task;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;

import java.io.InputStream;
import java.util.UUID;

import ai.api.AIServiceContext;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;

public class CrimePrediction extends AppCompatActivity {

    private String uuid = UUID.randomUUID().toString();
    private AIRequest aiRequest;
    private AIDataService aiDataService;
    private AIServiceContext customAIServiceContext;

    private SessionsClient sessionsClient;
    private SessionName session;


    private TextView tv;
    private EditText queryEdt;
    private Button btnQuery;
    String msg;
    ImageView botLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_prediction);

        botLogo = findViewById(R.id.botLogo);

        Glide.with(this).asGif().load(R.raw.logo).into(botLogo);

        btnQuery = findViewById(R.id.btnQuery);
        queryEdt = findViewById(R.id.edtQuery);

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        initV2Chatbot();
    }


    public void callbackV2(DetectIntentResponse response) {
        if (response != null) {
            // process aiResponse here
            String botReply = response.getQueryResult().getFulfillmentText();
            Toast.makeText(CrimePrediction.this, botReply, Toast.LENGTH_SHORT).show();

            if(botReply!=null){
                Intent intent = new Intent(CrimePrediction.this,MainAwarness.class);
                intent.putExtra("sug",botReply);
                startActivity(intent);
            }
        } else {
            Toast.makeText(CrimePrediction.this, "null", Toast.LENGTH_SHORT).show();
        }
    }
    private void sendMessage(){
        String msg = queryEdt.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(CrimePrediction.this, "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {
            // Android client for older V1 --- recommend not to use this
//            aiRequest.setQuery(msg);
//            RequestTask requestTask = new RequestTask(MainActivity.this, aiDataService, customAIServiceContext);
//            requestTask.execute(aiRequest);

            // Java V2
            QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(msg).setLanguageCode("en-US")).build();
            new RequestJavaV2Task(CrimePrediction.this, session, sessionsClient, queryInput).execute();

        }
    }


    private void initV2Chatbot() {
        try {
            InputStream stream = getResources().openRawResource(R.raw.test);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            String projectId = ((ServiceAccountCredentials)credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            session = SessionName.of(projectId, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
