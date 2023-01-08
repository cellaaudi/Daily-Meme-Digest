package com.cellaaudi.dailymemedigest

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        btnCreateAccNew.setOnClickListener {
            var username = txtUsernameNew.text.toString()
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
                        params.put("password", pass)
                        params.put("reg_date", date)
                        return params
                    }
                }
                q.add((stringRequest))
            } else {
                Toast.makeText(this, "Repeated password is different", Toast.LENGTH_SHORT).show()
            }
        }
    }
}