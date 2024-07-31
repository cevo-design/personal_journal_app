package com.example.personaljouurnalapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.personaljouurnalapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

  private lateinit var auth: FirebaseAuth
  private lateinit var binding: ActivitySignUpBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    binding = ActivitySignUpBinding.inflate(layoutInflater)
    setContentView(binding.root)

    auth = FirebaseAuth.getInstance()

    val username = binding.signUpUserName.text.toString()
    val password = binding.signUpPassword.text.toString()

    binding.signUpSubmitButton.setOnClickListener {

      Toast.makeText(this, "Username: $username", Toast.LENGTH_LONG).show()

      val intent = Intent(this, com.example.personaljouurnalapp.ui.login.LoginActivity::class.java)
      startActivity(intent)
      finish()


//      auth.createUserWithEmailAndPassword(username, password)
//        .addOnCompleteListener(this) { task ->
//          if (task.isSuccessful) {
//            val user = auth.currentUser
//            // Update UI with the signed-in user's information
//          } else {
//            Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_LONG).show()
//          }
//        }

    }
  }
}
