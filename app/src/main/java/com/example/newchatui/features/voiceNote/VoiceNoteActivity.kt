package com.example.newchatui.features.voiceNote

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.newchatui.R
import com.example.newchatui.api.RetrofitClient
import com.example.newchatui.model.ImageResponse
import com.google.android.gms.common.util.IOUtils.toByteArray
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_voice_note.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.io.InputStream


class VoiceNoteActivity : AppCompatActivity() {
    private var saved: Boolean = false
    private var sessionUri: Uri? = null
    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var recordingStopped: Boolean = false
    var storage = FirebaseStorage.getInstance()
    var sharedName: String = "sharedPreference"
var token:String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_note)

        mediaRecorder = MediaRecorder()
        output = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        mediaRecorder?.setOutputFile(output)

        val sharedPreferences = applicationContext!!.getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        token = sharedPreferences.getString("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjoie1wiX2lkXCI6XCI1ZjBhOWIyYzEwODJkNTM5YmM3YWE0YjdcIixcIm5hbWVcIjpcIkJoYXZpdCBLYW50aGFsaWFcIixcInBob25lTnVtYmVyXCI6NzU5NzkxNzAwNyxcIklNRUlcIjoxNjQ2MTU0LFwibGF0aXR1ZGVcIjoyMy40MzI1MjEsXCJsb25naXR1ZGVcIjo3My40MzI1MTUsXCJwYXNzd29yZFwiOlwiJDJhJDEwJGZ1WTJRODZTUjQwODQzVTZ5ZHVRbk9MNGtzT3lNT2NDRzhLVjAzd2hJM1cwZVUyNTNHQkZ1XCIsXCJjcmVhdGVkQXRcIjpcIjIwMjAtMDctMTJUMDU6MTA6MDQuMjI3WlwiLFwidXBkYXRlZEF0XCI6XCIyMDIwLTA3LTEyVDA1OjEwOjA0LjIyN1pcIixcIl9fdlwiOjB9IiwiaWF0IjoxNTk0NTMwNjUwfQ.2L12RwLY8d3l962WqWiGpBuJ2Qc14nVB1bDXD0uD23E")

        button_start_recording.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
            } else {
                startRecording()
            }
        }

        button_stop_recording.setOnClickListener{
            stopRecording()
        }

        button_pause_recording.setOnClickListener {
            pauseRecording()
        }
    }


    private fun startRecording() {
        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    private fun pauseRecording() {
        if(state) {
            if(!recordingStopped){
                Toast.makeText(this,"Stopped!", Toast.LENGTH_SHORT).show()
                mediaRecorder?.pause()
                recordingStopped = true
                button_pause_recording.text = "Resume"
            }else{
                resumeRecording()
            }
        }
    }

    @SuppressLint("RestrictedApi", "SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    private fun resumeRecording() {
        Toast.makeText(this,"Resume!", Toast.LENGTH_SHORT).show()
        mediaRecorder?.resume()
        button_pause_recording.text = "Pause"
        recordingStopped = false
    }

    private fun stopRecording(){
        if(state){
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
            val uriAudio: Uri = Uri.fromFile(File(output).getAbsoluteFile())
            val storageRef: StorageReference = storage.getReferenceFromUrl("gs://newchatui.appspot.com")
            var time = System.nanoTime().toString()
            var filename = time +".mp3"
            val mountainsRef = storageRef.child(time+".mp3")
            val mountainImagesRef = storageRef.child(filename)
            mountainsRef.getName().equals(mountainImagesRef.getName());    // true
            mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
            val outputFile = Environment.getExternalStorageDirectory()
                .absolutePath + "/recording.3gp"

            var mReference = mountainImagesRef
            try {
                mReference.putFile(uriAudio).addOnSuccessListener {
                    mReference.downloadUrl.addOnCompleteListener {
                        val url = it.result.toString()
                        Toast.makeText(applicationContext,url,Toast.LENGTH_SHORT).show()
                        if(url!=""){
                            uploadVoiceNote(url)
                        }
                    }
                }
            }catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }

        }
        else{
            Toast.makeText(this, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadVoiceNote(url: String) {
        RetrofitClient.instance.postVoiceNote(
            token,
            url,
            "24.48856",
            "75.258412"
        ).enqueue(object : Callback<ImageResponse> {
            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<ImageResponse>,
                response: Response<ImageResponse>
            ) {
                Toast.makeText(
                    applicationContext,
                    response.message().toString(),
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("api response","Complaint successfully registered")
            }

        })

    }
}
