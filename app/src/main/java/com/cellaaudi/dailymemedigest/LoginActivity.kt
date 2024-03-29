package com.cellaaudi.dailymemedigest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    companion object {
        var USER_ID = ""
        var USERNAME = ""
        var FIRST_NAME = ""
        var LAST_NAME: String? = ""
        var REG_DATE = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        var userName = Global.username

//        if (userName != "") {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//
//            finish()
//        }

        var sharedFile = "com.cellaaudi.dailymemedigest"
        var shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE )
        var editor: SharedPreferences.Editor = shared.edit()

        btnCreateAcc.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            var username = txtUsernameLogin.text.toString()
            var password = txtPassLogin.text.toString()

            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.fun/native/160420004/memes/login.php"
            val stringRequest = object : StringRequest(com.android.volley.Request.Method.POST, url,
                Response.Listener {
                    Log.d("cekparams", it)
                    var obj = JSONObject(it)
                    Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    if (obj.getString("result") == "success") {
                        val data = obj.getJSONObject("data")
                        editor.putInt("USER_ID", data.getInt("user_id"))
                        editor.putString("USERNAME", data.getString("username"))
                        editor.putString("FIRST_NAME", data.getString("first_name"))
                        editor.putString("LAST_NAME", data.getString("last_name"))
                        editor.putString("REG_DATE", data.getString("reg_date"))
                        editor.putString("SETTING", data.getString("privacy_setting"))
                        editor.apply()
//                        Global.login(username)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
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
                    params.put("password", password)
                    return params
                }
            }
            q.add((stringRequest))
        }
    }
}