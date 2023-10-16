package com.example.safestop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.safestop.database.FirebaseHelper
import com.example.safestop.databinding.ActivitySignUpBinding
import com.example.safestop.model.User
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var  binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    var firebaseHelper = FirebaseHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
            //if we click the textview navigation
        binding.textView.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        //if we click the button navigation
        binding.button.setOnClickListener{
            //matching the id's from the layout to get the value
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){

                    //create the user with balance
                    firebaseHelper.createUser(User(email,0.0), {
                        // Task creation was successful
//                        Toast.makeText(this, "Task created successfully", Toast.LENGTH_SHORT).show()
                        println("succes:${User(email,0.0)}")
                    }, { exception ->
                        // Task creation failed, handle the error
//                        Toast.makeText(this, "Task creation failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                        println("fail:${User(email,0.0)}")
                    })

                    //Creating username password into firebase by passing the value that we are entring
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
//                            var newUser = User(email,0.0)
//                            firebaseHelper.createUser(newUser)
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }

                }else{
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}