package com.example.smstexme // ⚠️ Replace with YOUR PACKAGE NAME  

import android.Manifest  
import android.content.pm.PackageManager  
import android.os.Bundle  
import android.telephony.SmsManager  
import android.widget.Button  
import android.widget.Toast  
import androidx.appcompat.app.AppCompatActivity  
import androidx.core.app.ActivityCompat  

class MainActivity : AppCompatActivity() {  
    private val SMS_PERMISSION_CODE = 101  

    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        setContentView(R.layout.activity_main)  

        val sendButton = findViewById<Button>(R.id.sendButton)  
        sendButton.setOnClickListener {  
            if (checkSmsPermission()) {  
                sendSMS()  
            } else {  
                requestSmsPermission()  
            }  
        }  
    }  

    private fun checkSmsPermission(): Boolean {  
        return ActivityCompat.checkSelfPermission(  
            this,  
            Manifest.permission.SEND_SMS  
        ) == PackageManager.PERMISSION_GRANTED  
    }  

    private fun requestSmsPermission() {  
        ActivityCompat.requestPermissions(  
            this,  
            arrayOf(Manifest.permission.SEND_SMS),  
            SMS_PERMISSION_CODE  
        )  
    }  

    private fun sendSMS() {  
        try {  
            val smsManager = SmsManager.getDefault()  
            smsManager.sendTextMessage(  
                "+1234567890", // ⚠️ Replace with YOUR PHONE NUMBER (e.g., "+447123456789")  
                null,  
                "Test SMS sent!",  
                null,  
                null  
            )  
            Toast.makeText(this, "SMS Sent!", Toast.LENGTH_SHORT).show()  
        } catch (e: Exception) {  
            Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_LONG).show()  
        }  
    }  

    override fun onRequestPermissionsResult(  
        requestCode: Int,  
        permissions: Array<String>,  
        grantResults: IntArray  
    ) {  
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)  
        if (requestCode == SMS_PERMISSION_CODE) {  
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {  
                sendSMS()  
            } else {  
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()  
            }  
        }  
    }  
}  
