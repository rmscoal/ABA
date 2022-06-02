package com.example.aba.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.aba.R
import com.example.aba.data.preferences.UserModel
import com.example.aba.databinding.FragmentHomeBinding
import com.example.aba.databinding.FragmentSettingBinding
import com.example.aba.ui.huruf.HurufActivity
import com.example.aba.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private var userModel= UserModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        //get userdata
        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
        val mUser = FirebaseAuth.getInstance().currentUser

        val test = userModel.email
        //get pencapaian
        //get progress
        getPencapaian()
        getProgress()

        //binding
        Glide.with(this)
            .load(mUser?.photoUrl)
            .circleCrop()
            .into(binding.ivAvatar)
        binding.ivNama.text = mUser?.displayName
        binding.tvProgress.text = "5/36"
        binding.tvPencapaian.text = "1/6"


        binding.conBelajarHuruf.setOnClickListener {
            startActivity(Intent(activity,HurufActivity::class.java))
        }
        return binding.root
    }

    private fun getProgress() {
        val data = userModel.eksplor_huruf

    }

    private fun getPencapaian() {

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}