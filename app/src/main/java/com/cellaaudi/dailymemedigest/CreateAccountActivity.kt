package com.cellaaudi.dailymemedigest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        btnCreateAccNew.setOnClickListener {
            var username = txtUsernameNew.text
            var pass = txtPassNew.text
            var pass2 = txtPass2New.text

            if (pass == pass2) {

            } else {
                Toast.makeText(this, "Repeated password is different", Toast.LENGTH_SHORT).show()
            }
        }
    }
}