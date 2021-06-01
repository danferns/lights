package com.cryptosonix.light

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cam = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        val main: Switch = findViewById(R.id.main)
        val second: Switch = findViewById(R.id.second)
        if (cam.getCameraCharacteristics(cam.cameraIdList[0]).get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true) {
            main.isChecked = false
            main.setOnClickListener {
                if (main.isChecked) {
                    cam.setTorchMode(cam.cameraIdList[0], true)
                } else {
                    cam.setTorchMode(cam.cameraIdList[0], false)
                }
            }
        } else {
            main.visibility = View.GONE
        }
        if (cam.getCameraCharacteristics(cam.cameraIdList[1]).get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true) {
            second.isChecked = false
            second.setOnClickListener {
                if (second.isChecked) {
                    cam.setTorchMode(cam.cameraIdList[1], true)
                } else {
                    cam.setTorchMode(cam.cameraIdList[1], false)
                }
            }
        } else {
            second.visibility = View.GONE
        }
    }
}