package com.cellaaudi.dailymemedigest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONObject
import kotlinx.android.synthetic.main.fragment_mine.view.*

class MineFragment : Fragment() {

    var memes: ArrayList<Meme> = ArrayList()
    var user_id = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedFile = "com.cellaaudi.dailymemedigest"
        var shared: SharedPreferences = view.context.getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        user_id = shared.getInt("USER_ID", 0)
    }

    override fun onResume() {
        super.onResume()

        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.fun/native/160420004/memes/getmine.php"
        var stringRequest = object: StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> {
                Log.d("apiresult", it)
                val obj = JSONObject(it)

                if (obj.getString("result") == "success") {
                    val data = obj.getJSONArray("data")
                    memes.clear()

                    for (i in 0 until data.length()) {
                        val memeObj = data.getJSONObject(i)
                        val meme = Meme(
                            memeObj.getInt("meme_id"),
                            memeObj.getString("image"),
                            memeObj.getString("top"),
                            memeObj.getString("bottom"),
                            memeObj.getInt("creator_id"),
                            memeObj.getInt("likes")
                        )
                        memes.add(meme)
                    }
                    updateList()
                    Log.d("cekisiarray", memes.toString())
                }
            },
            Response.ErrorListener {
                Log.e("apiresult", it.message.toString())
            }
        ) {
            override fun getParams(): Map<String, String?> {
                val params = HashMap<String, String?>()
                params.put("creator_id", user_id.toString())
                return params
            }
        }

        q.add(stringRequest)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MineFragment().apply {
            }
    }

    fun updateList() {
        val lm: LinearLayoutManager = LinearLayoutManager(activity)
        view?.mineView?.let {
            it.layoutManager = lm
            it.setHasFixedSize(true)
            it.adapter = MemeAdapter(memes)
        }
    }
}