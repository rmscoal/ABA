package com.example.aba.ui.huruf

import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.aba.R
import com.example.aba.databinding.ActivityHomeBinding
import com.example.aba.databinding.ActivityHurufBinding

class HurufActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHurufBinding

    //soundPool
    private lateinit var sp: SoundPool
    private lateinit var aa: AudioAttributes
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        aa= AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .build()

        //soundPool
        sp = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(aa)
            .build()
        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                spLoaded = true
            } else {
                Toast.makeText(this@HurufActivity, "Gagal load", Toast.LENGTH_SHORT).show()
            }

        }

        with(binding){
            hurufA.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.a, 1)
                Toast.makeText(this@HurufActivity, "A", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufB.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.b, 1)
                Toast.makeText(this@HurufActivity, "B", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufC.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.c, 1)
                Toast.makeText(this@HurufActivity, "C", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufD.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.d, 1)
                Toast.makeText(this@HurufActivity, "D", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufE.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.e, 1)
                Toast.makeText(this@HurufActivity, "E", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufF.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.f, 1)
                Toast.makeText(this@HurufActivity, "F", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufG.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.g, 1)
                Toast.makeText(this@HurufActivity, "G", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufH.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.h, 1)
                Toast.makeText(this@HurufActivity, "H", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufI.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.i, 1)
                Toast.makeText(this@HurufActivity, "I", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufJ.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.j, 1)
                Toast.makeText(this@HurufActivity, "J", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufK.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.k, 1)
                Toast.makeText(this@HurufActivity, "K", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufL.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.l, 1)
                Toast.makeText(this@HurufActivity, "L", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufM.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.m, 1)
                Toast.makeText(this@HurufActivity, "m", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufN.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.n, 1)
                Toast.makeText(this@HurufActivity, "N", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufO.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.o, 1)
                Toast.makeText(this@HurufActivity, "O", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufP.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.p, 1)
                Toast.makeText(this@HurufActivity, "Q", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufQ.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.q, 1)
                Toast.makeText(this@HurufActivity, "Q", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufR.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.r, 1)
                Toast.makeText(this@HurufActivity, "R", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufS.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.s, 1)
                Toast.makeText(this@HurufActivity, "S", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufT.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.t, 1)
                Toast.makeText(this@HurufActivity, "T", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufU.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.u, 1)
                Toast.makeText(this@HurufActivity, "U", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufV.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.v, 1)
                Toast.makeText(this@HurufActivity, "V", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufW.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.w, 1)
                Toast.makeText(this@HurufActivity, "W", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufX.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.x, 1)
                Toast.makeText(this@HurufActivity, "X", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufY.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.y, 1)
                Toast.makeText(this@HurufActivity, "Y", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
            hurufZ.setOnClickListener {
                soundId = sp.load(this@HurufActivity, R.raw.z, 1)
                Toast.makeText(this@HurufActivity, "Z", Toast.LENGTH_SHORT).show()

                if (spLoaded){
                    sp.play(soundId,1f,1f,0,0,1f)
                }
            }
        }
    }
}