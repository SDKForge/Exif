@file:Suppress("ktlint:standard:function-signature")

package dev.sdkforge.exif.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
import kotlin.experimental.ExperimentalObjCName
import platform.Foundation.NSURL
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerImageURL
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject

@OptIn(ExperimentalObjCName::class)
@Suppress("FunctionName")
@ObjCName("create")
fun ComposeAppViewController() = ComposeUIViewController(
    configure = {
        enforceStrictPlistSanityCheck = false
    },
) {
    val uiViewController = LocalUIViewController.current

    var path by remember { mutableStateOf<String?>(null) }

    val delegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingImage: UIImage,
                editingInfo: Map<Any?, *>?,
            ) {
                picker.dismissModalViewControllerAnimated(animated = true)
                path = (editingInfo?.get(UIImagePickerControllerImageURL) as? NSURL)?.absoluteString
            }

            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>,
            ) {
                picker.dismissModalViewControllerAnimated(animated = true)
                path = (didFinishPickingMediaWithInfo[UIImagePickerControllerImageURL] as? NSURL)?.absoluteString
            }

            override fun imagePickerControllerDidCancel(picker: UIImagePickerController) = Unit
        }
    }

    App(
        path = path,
        modifier = Modifier
            .fillMaxSize(),
        onImageSelectClick = {
            val imagePickerController = UIImagePickerController()
            imagePickerController.setDelegate(delegate)
            imagePickerController.setSourceType(UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary)

            uiViewController.presentViewController(
                viewControllerToPresent = imagePickerController,
                animated = true,
                completion = null,
            )
        },
    )
}
