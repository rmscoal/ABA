package com.example.aba.ui.latihanmengejahuruf

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.aba.databinding.ActivityRecordAudioBinding

class RecordAudioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordAudioBinding
    private lateinit var mr: MediaRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordAudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val path: String = Environment.getExternalStorageDirectory().toString()+"myrec.ogg"
        mr = MediaRecorder()

        binding.btStartRecord.isEnabled = false
        binding.btStopRecord.isEnabled = false

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO,
                                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE),111)
            binding.btStartRecord.isEnabled = true
        }
        binding.btStartRecord.setOnClickListener {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.OGG)
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mr.setOutputFile(path)
            mr.prepare()
            mr.start()
            binding.btStopRecord.isEnabled = true
            binding.btStartRecord.isEnabled = true
        }

        binding.btStopRecord.setOnClickListener {
            mr.stop()
            binding.btStartRecord.isEnabled = true
        }

        binding.btPlayRecord.setOnClickListener {
            var mp = MediaPlayer()
            mp.setDataSource(path)
            mp.prepare()
            mp.start()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            binding.btStartRecord.isEnabled = true
        }
    }

}