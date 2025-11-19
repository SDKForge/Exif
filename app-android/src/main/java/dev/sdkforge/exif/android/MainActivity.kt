package dev.sdkforge.exif.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.sdkforge.exif.app.App

class MainActivity : ComponentActivity() {
    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var path by remember { mutableStateOf<String?>(null) }

            val imagePicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
            ) { uri ->
                path = uri?.toString()
            }

            App(
                path = path,
                modifier = Modifier
                    .fillMaxSize(),
                onImageSelectClick = {
                    imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
            )
        }
    }
}
