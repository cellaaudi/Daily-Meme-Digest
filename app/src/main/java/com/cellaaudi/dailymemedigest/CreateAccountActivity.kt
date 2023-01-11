package com.cellaaudi.dailymemedigest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_create_account.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CreateAccountActivity : AppCompatActivity() {
    val REQUEST_SELECT_AVATAR = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        btnCreateAccNew.setOnClickListener {
//            avatarNew.setOnClickListener {
//                val intent = Intent(Intent.ACTION_PICK)
//                intent.setType("image/*")
//                startActivityForResult(intent, REQUEST_SELECT_AVATAR)
//            }

            var username = txtUsernameNew.text.toString()
            var first_name = txtNameNew.text.toString()
            var last_name = txtSurnameNew?.text.toString()
            var pass = txtPassNew.text.toString()
            var pass2 = txtPass2New.text.toString()

            val sdf = SimpleDateFormat("MMMM yyyy")
            val date = sdf.format(Date()).toString()

            if (pass == pass2) {
                val q = Volley.newRequestQueue(this)
                val url = "https://ubaya.fun/native/160420004/memes/createaccount.php"
                val stringRequest = object : StringRequest(com.android.volley.Request.Method.POST, url,
                    Response.Listener {
                        Log.d("cekparams", it)
                        var obj = JSONObject(it)
                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        if (obj.getString("result") == "success") {
                            finish()
                        }
                    },
                    Response.ErrorListener {
                        Log.d("cekparams2", it.message.toString())
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): Map<String, String?> {
                        val params = HashMap<String, String?>()
                        params.put("username", username)
                        params.put("first_name", first_name)
                        if (last_name != null) {
                            params.put("last_name", last_name)
                        }
                        params.put("password", pass)
                        params.put("reg_date", date)
//                        params.put("avatar", "")
                        return params
                    }
                }
                q.add((stringRequest))
            } else {
                Toast.makeText(this, "Repeated password is different", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == Activity.RE)
//        }
//    }
}