package dev.sdkforge.exif.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sdkforge.exif.data.Exif

@Composable
fun App(
    path: String?,
    modifier: Modifier = Modifier,
    onImageSelectClick: () -> Unit = {},
) = ApplicationTheme {
    val exif = remember(path) { Exif.parse(path) }

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Orientation: ${exif?.orientation}",
            )
            Text(
                text = "Image width: ${exif?.imageWidth}",
            )
            Text(
                text = "Image height: ${exif?.imageHeight}",
            )

            Button(
                onClick = onImageSelectClick,
            ) {
                Text(
                    text = "Select image",
                )
            }
        }
    }
}
