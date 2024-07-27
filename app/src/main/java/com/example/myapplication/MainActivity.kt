package com.example.myapplication

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.webkit.PermissionRequest
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.security.Permission
import java.security.Permissions
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), OnClickListener {

    val LAUNCHER: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->Log.d (LOG_MSG, "result code:" + result.resultCode)
            if (result.resultCode == Activity.RESULT_OK){
                Log.d(LOG_MSG, "result code ok")
                var message = result.data?.extras?.getInt("returning")
                Log.d(LOG_MSG, "return message:" + message)
            } else if (result.resultCode==Activity.RESULT_CANCELED) {
                Log.d(LOG_MSG, "result code canceled")
            }
        }

    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var plus: Button
    lateinit var preference_counter:TextView
    val LOG_MSG:String="2"
    //lateinit var
    val dataStore: DataStore<Preferences> by preferencesDataStore(name="settings")
    val EXAMPLE_COUNTER= intPreferencesKey("example_counter")
    lateinit var h_View: herdaView
    lateinit var h_View_count: TextView
    val n_request : Int = 1
    lateinit var broadcast:Button
    var broadcastReceiver: ConnectionReceiver? = null
    lateinit var sendBroadcast:Button
    var customBroadcastReceiver2: customBroadcastReceiver?=null
    lateinit var mNotificationManager: NotificationManager
    lateinit var button_notification: Button
    lateinit var button_exp_not:Button
    lateinit var button_pend_not: Button
    lateinit var button_act_not: Button
    lateinit var start_service: Button
    lateinit var stop_service: Button
    private lateinit var mService: MyServiceBind
    private var mBound: Boolean=false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected (className: ComponentName, service: IBinder){
            val binder = service as MyServiceBind.LocalBinder
            mService = binder.getService()
            mBound=true
        }
        override fun onServiceDisconnected (arg0:ComponentName){
            mBound=false
        }
    }
    lateinit var button_bind: Button
    lateinit var button_unbind: Button
    lateinit var button_img:Button
    lateinit var work_man_not:Button
    lateinit var work_not_send:Button
    lateinit var work_not_inf: Button
    lateinit var button_cadastro: Button
    lateinit var button_login: Button
    lateinit var button_movie_rec : Button
    lateinit var sim_movies: Button
    lateinit var sim_fav_movies: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        button1=findViewById<Button>(R.id.button1) as Button
        button2=findViewById<Button>(R.id.button2) as Button
        //var create_alarm = findViewbyId(R.id.create_alarm) as Button
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        plus=findViewById<Button>(R.id.plus) as Button
        plus.setOnClickListener(this)
        preference_counter=findViewById<TextView>(R.id.pref_count)
        h_View_count=findViewById<TextView>(R.id.h_view_count)
        broadcast=findViewById<Button>(R.id.broadcast)
        broadcast.setOnClickListener(this)
        sendBroadcast=findViewById<Button>(R.id.send_broadcast)
        sendBroadcast.setOnClickListener(this)
        button_notification=findViewById<Button>(R.id.basic_notification) as Button
        button_notification.setOnClickListener(this)
        button_exp_not=findViewById<Button>(R.id.expanded_notification) as Button
        button_exp_not.setOnClickListener(this)
        button_pend_not=findViewById<Button>(R.id.pending_intent_notification) as Button
        button_pend_not.setOnClickListener(this)
        button_act_not=findViewById<Button>(R.id.notification_action) as Button
        button_act_not.setOnClickListener(this)
        start_service=findViewById<Button>(R.id.start_service) as Button
        start_service.setOnClickListener(this)
        stop_service=findViewById<Button>(R.id.stop_service) as Button
        stop_service.setOnClickListener(this)
        button_bind=findViewById<Button>(R.id.check_service_bind) as Button
        button_bind.setOnClickListener(this)
        button_unbind=findViewById<Button>(R.id.check_service_unbind) as Button
        button_unbind.setOnClickListener(this)
        button_img=findViewById<Button>(R.id.notification_image) as Button
        button_img.setOnClickListener(this)
        work_man_not=findViewById<Button>(R.id.workmanager_notification) as Button
        work_man_not.setOnClickListener(this)
        work_not_send=findViewById<Button>(R.id.workmanager_notification_send_msg) as Button
        work_not_send.setOnClickListener(this)
        work_not_inf=findViewById<Button>(R.id.workmanager_notification_info) as Button
        work_not_inf.setOnClickListener(this)
        button_cadastro = findViewById<Button>(R.id.cadastrar) as Button
        button_cadastro.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        button_login = findViewById <Button>(R.id.login_main) as Button
        button_login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        button_movie_rec=findViewById <Button> (R.id.pagfilmes) as Button
        button_movie_rec.setOnClickListener{
            val intent = Intent(this, ResearchMovieActivity::class.java)
            startActivity(intent)
        }
        sim_movies=findViewById <Button> (R.id.sim_movies) as Button
        sim_movies.setOnClickListener{
            val intent = Intent(this, SimilarMovies2::class.java)
            startActivity(intent)
        }
        sim_fav_movies=findViewById <Button> (R.id.sim_fav_movies) as Button
        sim_fav_movies.setOnClickListener{
            val intent = Intent(this, SimilarFavMovies::class.java)
            startActivity(intent)
        }

        //R.id criar botao, setar id, linkar layout no oncreate, linkar botao a var,
        // setar escutador, implemebnntar click
        lifecycleScope.launch {
            //IncrementPreferenceCounting()
            ReadPreferenceCounter()
        }

        h_View= ViewModelProvider(this).get(herdaView::class.java)
        h_View_count.text=h_View.getCounter()

        //fourthAct_viewModel.getCounter2().observe(this, Observer{
            //fourthAct_counter2.setText(.toString())
        //})

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, arrayOf (android.Manifest.permission.ACCESS_FINE_LOCATION), n_request)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf (android.Manifest.permission.ACCESS_FINE_LOCATION), n_request)

            }
        }

        //gerenciador on create

        mNotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            val channel = NotificationChannel ("MY_CHANNEL_ID", "MY_CHANNEL_NAME",
            NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableLights (true)
            channel.lightColor= Color.BLACK
            channel.description= "MY_NOTIFICATION_CHANNEL_DESCRIPTION"
            mNotificationManager.createNotificationChannel(channel)
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.POST_NOTIFICATIONS)) {
                ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.POST_NOTIFICATIONS), 111)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf( android.Manifest.permission.POST_NOTIFICATIONS), 111)
            }
        }
        Intent (this, MyServiceBind:: class.java).also{ intent->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("teste", "start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("teste2", "resume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("teste3", "stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("teste4", "destroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("teste5", "restart")
    }

    override fun onClick(v: View?) {
        //TODO("Not yet implemented")
        when (v?.id) {
            R.id.create_alarm -> {
                Log.d(LOG_MSG, "create alarm")
                val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply{
                    putExtra(AlarmClock.EXTRA_MESSAGE, "P3")
                    putExtra(AlarmClock.EXTRA_HOUR, 19)
                    putExtra(AlarmClock.EXTRA_MINUTES, 0)
                }
                if (intent.resolveActivity(packageManager)!=null){
                    startActivity(intent)
                }
            }

            R.id.button1->{

            }
            R.id.button2->{
                val intent= Intent(this, SecondActivity::class.java)
                intent.putExtra("data", "message from caller")
                LAUNCHER.launch(intent)

            }
            R.id.plus-> {
                lifecycleScope.launch {
                    IncrementPreferenceCounting()
                    ReadPreferenceCounter()
                }
            }
            R.id.h_view_add ->{
                h_View.addOne()
                h_View_count.text=h_View.getCounter()
            }

//            R.id.broadcast->{
//                if (broadcastReceiver == null) {
//                    broadcastReceiver= ConnectionReceiver()
//                    IntentFilter (Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
//                        registerReceiver(broadcastReceiver, it)
//                    }
//                    Toast.makeText(this, "broadcast registered", Toast.LENGTH_LONG).show()
//                }else {
//                    unregisterReceiver(broadcastReceiver)
//                    broadcastReceiver = null
//                    Toast.makeText(this, "broadcast Unregistered", Toast.LENGTH_LONG).show()
//                }
//            }

            R.id.broadcast->{
                if (customBroadcastReceiver2==null) {
                //if (broadcastReceiver == null) {
                    customBroadcastReceiver2 = customBroadcastReceiver()
                    //broadcastReceiver= ConnectionReceiver()
                    IntentFilter ("com.aimiris.demos.blah").also {
                        registerReceiver(customBroadcastReceiver2, it, ContextCompat.RECEIVER_EXPORTED)
                        //registerReceiver(broadcastReceiver, it, ContextCompat.RECEIVER_EXPORTED)
                        //registerReceiver(customBroadcastReceiver, it)
                    }
                    Toast.makeText(this, "broadcast registered", Toast.LENGTH_LONG).show()
                }else {
                    //unregisterReceiver(broadcastReceiver)
                    unregisterReceiver(customBroadcastReceiver2)
                    //broadcastReceiver = null
                    customBroadcastReceiver2=null
                    Toast.makeText(this, "broadcast Unregistered", Toast.LENGTH_LONG).show()
                }
            }

            R.id.send_broadcast->{
                Log.d(LOG_MSG, "send broadcast click")
                val intent=Intent()
                intent.setAction("com.aimiris.demos.blah")
                intent.putExtra("data", "Nothing to see here, move along")
                sendBroadcast(intent)
            }

            //manualmente permitir notificações

            R.id.basic_notification -> {
                val mBuilder = NotificationCompat.Builder (applicationContext, "MY_CHANNEL_ID")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("P3")
                    .setContentText("Tem aula hj")
                    .setAutoCancel(true)
                mNotificationManager.notify(0, mBuilder.build())
            }

            R.id.expanded_notification -> {
                val mBuilder = NotificationCompat.Builder (applicationContext, "MY_CHANNEL_ID")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("P3")
                    .setContentText("Não tem aula hj")
                    .setAutoCancel(true)
                    .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("Been trying hard not to get into trouble but I've got a war on my mind"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                mNotificationManager.notify(0, mBuilder.build())
            }

            R.id.pending_intent_notification -> {
                val mBuilder = NotificationCompat.Builder (applicationContext, "MY_CHANNEL_ID")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("P3")
                    .setContentText("Slides no classroom")
                    .setAutoCancel(true)
                val intent = Intent (applicationContext, SecondActivity::class.java)
                val pi= PendingIntent.getActivity (this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                mBuilder.setContentIntent(pi)
                mNotificationManager.notify(0, mBuilder.build())
            }

            //cria 1 intent, chamando o broadcast, 4, chamando a intent, passando classe broadcast, broadcast q vou escutar,
            // crio o pending intent usando o intent, passando a ação no intent

            R.id.notification_action -> {
                val snoozeIntent= Intent (this, customBroadcastReceiver::class.java).apply{
                    action= "com;aimriris.demos.blah"
                    putExtra("data","1994")
                }
                val snoozePendingIntent:PendingIntent=
                    PendingIntent.getBroadcast (this, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)
                val mBuilder = NotificationCompat.Builder (applicationContext, "MY_CHANNEL_ID")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("P3")
                    .setContentText("Slides no classroom")
                    .setContentIntent(snoozePendingIntent)
                    .setPriority (NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(R.drawable.img, "test", snoozePendingIntent)
                    .setAutoCancel(true)
                mNotificationManager.notify(0, mBuilder.build())
            }
            //imagem pasta drawable

            //ver notification reply pt1 e pt2
            R.id.notification_reply-> {
                val KEY_TEXT_REPLY = "data"
                val snoozeIntent = Intent(this, customBroadcastReceiver::class.java).apply {
                    action = "com.aimiris.demos.blah2"
                }
                var replyLabel: String = "u gonna answer?"
                //var remoteInókff
                val mBuilder = NotificationCompat.Builder(this, "MY_CHANNEL_ID2")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("P3")
                    .setContentText("je suis tres fatigue")
                    //.setAction(action)
                    .setAutoCancel(true)
                mNotificationManager.notify(0, mBuilder.build())
            }

            //falta colocar esse botao no main
            R.id.notification_group->{
                val channelGroup: NotificationChannelGroup
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    channelGroup=NotificationChannelGroup ("MY_CHANNELS_GROUP_ID", "MY_CHANNEL_GROUP")
                    mNotificationManager.createNotificationChannelGroup(channelGroup);

                    val channel2: NotificationChannel=NotificationChannel(
                        "MY_CHANNEL_ID2",
                        "MY_CHANNEL_NAME2",
                        NotificationManager.IMPORTANCE_DEFAULT);

                    val channel3: NotificationChannel=NotificationChannel(
                        "MY_CHANNEL_ID3",
                        "MY_CHANNEL_NAME3",
                        NotificationManager.IMPORTANCE_DEFAULT);

                    channel2.group="MY_CHANNELS_GROUP_ID"
                    channel3.group="MY_CHANNELS_GROUP_ID"

                    mNotificationManager.createNotificationChannel(channel2);
                    mNotificationManager.createNotificationChannel(channel3);

                    val mBuilder2 = NotificationCompat.Builder(this, "MY_CHANNEL_ID2")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("P3")
                        .setContentText("je suis tres fatigue")
                    .setAutoCancel(true)
                    mNotificationManager.notify(0, mBuilder2.build())
                }
            }

            R.id.start_service -> {
                val intent:Intent = Intent (this, MyService::class.java)
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    startForegroundService(intent)
                }
            }

            R.id.stop_service -> {
                val intent:Intent = Intent (this, MyService::class.java)
                stopService(intent)
            }

            R.id.check_service_unbind -> {
                unbindService (connection)
                mBound=false
            }
            R.id.check_service_bind -> {
                if (mBound) {
                    val num: Int = mService.randomNumber
                    Toast.makeText(this, "binded service: " + num, Toast.LENGTH_LONG).show()

                }
            }

            R.id.notification_image->{
                val mBuilder = NotificationCompat.Builder (this, "MY_CHANNEL_ID")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("P3")
                    .setContentText("choveu dms")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img))
                    .setStyle (NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.img))
                        .bigLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.img)))
                    .setAutoCancel (true)
                mNotificationManager.notify (1, mBuilder.build())
            }

            R.id.workmanager_notification->{
                val notificationWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(MyNotificationWorkManager:: class.java)
                    .setInitialDelay(3, TimeUnit.SECONDS)
                    .build()
                WorkManager.getInstance(this).enqueue (notificationWorkRequest)
            }

            R.id.workmanager_notification_send_msg->{
                val inputData= Data.Builder()
                    .putString("data1", "I wish I was watching Netflix fr")
                    .putBoolean("data2", true)
                    .build()
                val notificationWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(MyNotificationWorkManager::class.java)
                    .setInputData(inputData)
                    .build()
                WorkManager.getInstance(this).enqueue (notificationWorkRequest)
            }

            R.id.workmanager_notification_info-> {
                val notificationWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(MyNotificationWorkManager::class.java)
                    .setInitialDelay(3, TimeUnit.SECONDS)
                    .build()

                WorkManager.getInstance(this).enqueue(notificationWorkRequest)
                WorkManager.getInstance(this).getWorkInfoByIdLiveData(notificationWorkRequest.id)
                    .observeForever() { it ->
                        when (it.state) {
                            WorkInfo.State.ENQUEUED -> {Log.d(LOG_MSG, "ENQUEUED")}
                            WorkInfo.State.RUNNING -> {Log.d(LOG_MSG, "RUNNING")}
                            WorkInfo.State.SUCCEEDED -> {Log.d(LOG_MSG, "SUCCEEDED")}
                            WorkInfo.State.FAILED -> {Log.d(LOG_MSG, "FAILED")}
                            WorkInfo.State.BLOCKED -> {Log.d(LOG_MSG, "BLOCKED")}
                            WorkInfo.State.CANCELLED -> {Log.d(LOG_MSG, "CANCELLED")}
                        }
                    }
            }

            R.id.cadastrar->{
                val intent= Intent(this, Register::class.java)
                //intent.putExtra("data", "message from caller")
                //LAUNCHER.launch(intent)
                startActivity(intent)

            }

            R.id.login_main->{
                val intent= Intent(this, Login::class.java)
                //intent.putExtra("data", "message from caller")
                //LAUNCHER.launch(intent)
                startActivity(intent)

            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==n_request)
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "not ok", Toast.LENGTH_SHORT).show()
            }
    }


    suspend fun ReadPreferenceCounter (){
        val readPreferenceCounter: Flow<Int> = dataStore.data.catch{exception->
            if (exception is IOException){
                Log.e(LOG_MSG, "Error reading preferences.", exception)
            } else {
                throw exception
            }
        }.map {preferences-> preferences[EXAMPLE_COUNTER]?:0
        }
        //Log.d("erro", readPreferenceCounter.first().toString())
        preference_counter.setText(readPreferenceCounter.first().toString())
    }

    suspend fun IncrementPreferenceCounting(){
        dataStore.edit { settings->
            val currentCounterValue = settings [EXAMPLE_COUNTER] ?: 0
            settings [EXAMPLE_COUNTER] = currentCounterValue + 1
        }
    }
}