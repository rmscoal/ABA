package com.example.aba.ui.latihanmengejahuruf

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.aba.databinding.ActivityHurufBinding
import com.example.aba.databinding.ActivityRecordAudioBinding
import java.io.IOException

class RecordAudioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordAudioBinding
    private lateinit var mr: MediaRecorder

    private var output: String? = null
    private var mediaRecorder: MediaRecorder? = null
    private var state: Boolean = false
    private var recordingStopped: Boolean = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaRecorder = MediaRecorder()
        output = Environment.getExternalStorageDirectory().absolutePath + "/recording.3gp"

        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder?.setOutputFile(output)

        binding.btStartRecord.setOnClickListener {
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                ActivityCompat.requestPermissions(this, permissions,0)
//            } else {
//                startRecording()
//            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            }
        }

        binding.btStopRecord.setOnClickListener{
            stopRecording()
        }
//
//        button_pause_recording.setOnClickListener {
//            pauseRecording()
//        }
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
//                button_pause_recording.text = "Resume"
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
//        button_pause_recording.text = "Pause"
        recordingStopped = false
    }

    private fun stopRecording(){
        if(state){
            mediaRecorder?.stop()
            mediaRecorder?.release()
            state = false
        }else{
            Toast.makeText(this, "You are not recording right now!", Toast.LENGTH_SHORT).show()
        }
    }
}