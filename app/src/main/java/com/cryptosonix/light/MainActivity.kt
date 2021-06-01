package com.cryptosonix.light

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cam = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val switchboard: LinearLayout = findViewById(R.id.switchboard)
        val lensNames = arrayOf("Front Camera", "Back Camera", "External Camera", "Camera")

        for (camera_id in cam.cameraIdList) {
            val props = cam.getCameraCharacteristics(camera_id)
            if (props.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true) {
                val switch = Switch(this)
                switch.text = lensNames[props.get(CameraCharacteristics.LENS_FACING)  ?: 3]

                switch.setOnClickListener{
                    if (switch.isChecked) {
                        cam.setTorchMode(camera_id, true)
                    } else {
                        cam.setTorchMode(camera_id, false)
                    }
                }

                switchboard.addView(switch)
            }
        }
    }
}