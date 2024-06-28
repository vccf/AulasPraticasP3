package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Register : AppCompatActivity(), View.OnClickListener {

    lateinit var buttonCad: Button
    val LOG = "not working"
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(applicationContext)
    }

    var userDetails=userDetails()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.register)

        buttonCad = findViewById<Button>(R.id.cadastro) as Button
        buttonCad.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cadastro -> {
                val request: StringRequest = object : StringRequest(
                    Method.POST,
                    "http://172.22.73.167:3000/registerUser",
                    object : Response.Listener<String> {
                        override fun onResponse(p0: String?) {
                            val jObject: JSONObject = JSONObject(p0!!)
                            val serverErrors: Boolean = jObject.getBoolean("errors")
                        }
                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(p0: VolleyError?) {
                            Log.e(LOG, "login request error: " + p0?.message)
                        }
                    }
                ) {
                    override fun getParams(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap<String, String>()
                        params["email"] = userDetails.email
                        params["phone"] = userDetails.phone
                        params["name"] = userDetails.name
                        params["cpf"] = userDetails.cpf
                        //params["birth"] = Utils.SQLDateFormat(userDetails.birth)
                        params["birth"] = userDetails.birth
                        params["gender"] = userDetails.gender
                        params["profilePic"] = userDetails.profilePic
                        params["username"] = userDetails.username
                        params["password"] = userDetails.password
                        return params
                    }
                }

                requestQueue.add(request)

            }
        }
    }
}