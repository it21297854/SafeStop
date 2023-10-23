package com.example.safestop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.safestop.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener{
            //matching the id's from the layout to get the value
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            //checking email and password null or not getting from ui elements
            if (email.isNotEmpty() && pass.isNotEmpty() ){
                    //signing in with username password into firebase
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            //getting userid from the email and initialize to the userId
                            val userId = email.substringBefore("@")

                            //redirect to the main page after successfull login
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("userID", userId)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                 Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }