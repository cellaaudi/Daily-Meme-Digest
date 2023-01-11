package com.cellaaudi.dailymemedigest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_create_meme.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.json.JSONObject

class CreateMemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_meme)

        var sharedFile = "com.cellaaudi.dailymemedigest"
        var shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        var user_id = shared.getInt("USER_ID", 0)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Create Your Meme"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        txtIMGURL.doOnTextChanged { text, start, before, count ->
            if (text.toString().length > 0) {
                Picasso.get().load(text.toString()).into(imgMemeAdd)
            }
        }

        txtTopTextAdd.doOnTextChanged { text, start, before, count ->
            if (text.toString().length > 0) {
                txtTopAdd.text = text
            }
        }

        txtBottomTextAdd.doOnTextChanged { text, start, before, count ->
            if (text.toString().length > 0) {
                txtBottomAdd.text = text
            }
        }

        btnCreateMeme.setOnClickListener {
            var img = txtIMGURL.text.toString()
            var top = txtTopTextAdd.text.toString()
            var bottom = txtBottomTextAdd.text.toString()

            if (img.length > 0 && top.length > 0 && bottom.length > 0) {
                val q = Volley.newRequestQueue(this)
                val url = "https://ubaya.fun/native/160420004/memes/creatememe.php"
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
                        params.put("image", img)
                        params.put("top", top)
                        params.put("bottom", bottom)
                        params.put("creator_id", user_id.toString())
                        return params
                    }
                }
                q.add((stringRequest))
            } else {
                Toast.makeText(this, "Make sure you've filled all the fields above", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}