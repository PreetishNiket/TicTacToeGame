package com.example.tictactoe

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.Rating
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager

import android.view.LayoutInflater

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.rate_custom_layout.view.*


class HomeActivity : AppCompatActivity(){
    private lateinit var mediaPlayer: MediaPlayer
    private  var rat=0F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        //val animDeep = AnimationUtils.loadAnimation(applicationContext, R.anim.deep)
        mediaPlayer= MediaPlayer.create(this,R.raw.button_pop)
        mediaPlayer.setVolume(10F,10F)
        mediaPlayer.isLooping=false

        multi.setOnClickListener {
            //multi.startAnimation(animDeep)
            startActivity(Intent(this,MainActivity::class.java))
            mediaPlayer.start()
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

        vsAndroid.setOnClickListener {
           // vsAndroid.startAnimation(animDeep)
            mediaPlayer.start()
            startActivity(Intent(this,AndroidActivity::class.java))
            //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
        rate_us_btn.setOnClickListener {
            //rate_us_btn.startAnimation(animDeep)
//            val number="+919599478721"
//            val num= String.format("smsto:",number)

            Toast.makeText(this,"Please Grant Sms Permission Manually in App info,If you want your feedback to reach me",Toast.LENGTH_LONG).show()
            mediaPlayer.start()
            val dialog= AlertDialog.Builder(this)
            val dialogView= LayoutInflater.from(this).inflate(R.layout.rate_custom_layout,null)
            dialog.setView(dialogView)
            val alertDialog=dialog.show()
            dialogView.ratingBar.rating=load()
            dialogView.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                rat=rating
                save(rat)
                alertDialog.dismiss()

                val perms= arrayOf(Manifest.permission.SEND_SMS)
                if (ContextCompat.checkSelfPermission(baseContext,
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.SEND_SMS))
                    {
                        ActivityCompat.requestPermissions(this,
                            perms, 1);
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(this,
                            perms, 1);
                    }

                }
                else{
                    val smsManager=SmsManager.getDefault()
                    smsManager.sendTextMessage("+919599478721",null,"Rating:$rat",null,null)
                }

            }
            alertDialog.show()


           // startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageManager")))

        }
        owner_info.setOnClickListener {
            //owner_info.startAnimation(animDeep)
            mediaPlayer.start()
            startActivity(Intent(this,OwnerActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode)
        {
            1->{
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.SEND_SMS) ==  PackageManager.PERMISSION_GRANTED)
                    {
                        //Toast.makeText(this, "Permission granted,Now Rate us", Toast.LENGTH_SHORT).show();
                        val smsManager=SmsManager.getDefault()
                        smsManager.sendTextMessage("+919599478721",null,"Rating:${rat}",null,null)
                    }
                }
                else
                {
                    Toast.makeText(this, "No Permission granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    private fun save(f:Float)
    {
        val pref=getSharedPreferences("save", Context.MODE_PRIVATE)
        val editor=pref.edit()
        editor.putFloat("rating",f)
        editor.apply()
    }
    private fun load(): Float {
        val sharedPref=getSharedPreferences("save", Context.MODE_PRIVATE)
        val f=sharedPref.getFloat("rating",0F)
        return f
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        // Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        StyleableToast.makeText(this,"Please click BACK again to exit", Toast.LENGTH_SHORT,R.style.exit).show()
    }

    override fun onResume() {
        super.onResume()
        doubleBackToExitPressedOnce = false
    }
}