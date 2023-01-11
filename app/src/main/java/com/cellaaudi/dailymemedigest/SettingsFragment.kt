package com.cellaaudi.dailymemedigest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedFile = "com.cellaaudi.dailymemedigest"
        var shared: SharedPreferences = view.context.getSharedPreferences(sharedFile, Context.MODE_PRIVATE )
        var editor: SharedPreferences.Editor = shared.edit()

        var user_id = shared.getInt("USER_ID", 0)
        var username = shared.getString("USERNAME", "username")
        var first_name = shared.getString("FIRST_NAME", "first")
        var last_name: String? = shared?.getString("LAST_NAME", "last")
        var reg_date = shared.getString("REG_DATE", "reg_date")
        var setting = shared.getString("SETTING", "Show")

        txtNameSet.text = first_name + last_name
        txtSinceSet.text = "Active since " + reg_date
        txtUsernameSet.text = username
        txtFirstNameSet.setText(first_name)
        if (last_name != null) {
            txtSurnameSet.setText(last_name)
        }
        if (setting == "Show") {
            cbHide!!.isChecked = false
        } else if (setting == "Hide") {
            cbHide!!.isChecked = true
        }

        fabOut.setOnClickListener {
            activity?.let {
                editor.clear()
                editor.apply()

                val intent = Intent(it, LoginActivity::class.java)
                it.startActivity(intent)
            }
        }

        btnSaveSet.setOnClickListener {
            if (txtFirstNameSet.text.toString().length > 0) {

            } else {
                Toast.makeText(activity, "At least give us your first name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {

            }
    }
}