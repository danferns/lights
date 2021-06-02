package com.cryptosonix.light

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CameraManager.TorchCallback
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
        val lensNames = arrayOf(
            "Front Camera Flash",
            "Back Camera Flash",
            "External Camera Flash",
            "Camera Flash"
        )

        val availableFlashlights = ArrayList<String>()
        val switches = ArrayList<Switch>()

        val torchCallback: TorchCallback = object : TorchCallback() {
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                super.onTorchModeChanged(cameraId, enabled)
                if (cameraId in availableFlashlights) { // a known flashlight changed state
                    val switch: Switch = switches[availableFlashlights.indexOf(cameraId)]
                    switch.isChecked = enabled
                } else { // initialize new flashlight
                    val switch = Switch(this@MainActivity)
                    switch.text = lensNames[cam.getCameraCharacteristics(cameraId)
                        .get(CameraCharacteristics.LENS_FACING) ?: 3]
                    switch.isChecked = enabled

                    availableFlashlights.add(cameraId)
                    switches.add(switch)

                    switch.setOnClickListener {
                        cam.setTorchMode(cameraId, switch.isChecked)
                    }
                    switchboard.addView(switch)
                }
            }
        }

        cam.registerTorchCallback(torchCallback, null)
    }
}