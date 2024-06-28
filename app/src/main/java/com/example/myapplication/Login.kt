package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Login : AppCompatActivity(), View.OnClickListener {

    //http://172.22.73.167:3000/

    lateinit var buttonLog: Button
    val LOG = "not working"
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(applicationContext)
    }

    var userDetails= userDetails()

    lateinit var username:EditText
    lateinit var password:EditText

    //findView on Create, Edit Text

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.login)

        buttonLog = findViewById<Button>(R.id.login_page) as Button
        buttonLog.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_page->{
                Log.d("asdf", "asfdsa")
                val request: StringRequest = object: StringRequest(
                    Method.POST,
                    "http://172.22.73.167:3000/login",
                    object: Response.Listener<String>{
                        override fun onResponse(p0: String?) {
                            val jObject: JSONObject = JSONObject(p0!!)
                            val serverErrors: Boolean = jObject.getBoolean("errors")
                        }
                    },
                    object: Response.ErrorListener {
                        override fun onErrorResponse (p0: VolleyError?) {
                            Log.e(LOG, "login request error: " + p0?.message)
                        }
                    }
                )
                {
                    override fun getParams(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap<String, String>()
//                        params["username"] = userDetails.username
//                        params["password"] = userDetails.password
                        params["username"] = "username.text.toString()"
                        params["password"] = "password.text.toString()"
                        return params
                    }
                }

                requestQueue.add(request)
            }
        }
    }
}