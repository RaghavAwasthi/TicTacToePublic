package com.rvai.tictactoe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUserMetadata
import com.jakewharton.threetenabp.AndroidThreeTen
import com.rvai.tictactoe.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    lateinit var binding: ActivityRootBinding
    var mauth: FirebaseAuth = FirebaseAuth.getInstance()
    var RC_SIGN_IN = 153

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this)
        signIn()
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.local.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)

        }

        binding.multiplayer.setOnClickListener {
            val i = Intent(this, ModeSelectActivity::class.java)
            startActivity(i)

        }


    }

    fun signIn() {
        if (mauth.getCurrentUser() != null) {

            // already signed in
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder().setLogo(R.mipmap.ic_launcher)
                            .setIsSmartLockEnabled(false, true)
                            .setAlwaysShowSignInMethodScreen(false)
                            .setAvailableProviders(listOf(
                                    AuthUI.IdpConfig.EmailBuilder().build()))
                            .build(),
                    RC_SIGN_IN)
            // not signed in
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response: IdpResponse? = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val metadata: FirebaseUserMetadata? = mauth.getCurrentUser()!!.getMetadata()

            }
        }
    }
}
