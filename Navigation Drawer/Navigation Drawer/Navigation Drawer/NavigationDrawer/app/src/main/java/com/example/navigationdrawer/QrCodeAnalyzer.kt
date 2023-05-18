package com.example.navigationdrawer

import android.graphics.ImageFormat
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.lang.Exception
import java.nio.ByteBuffer

class QrCodeAnalyzer(
    private val onQrCodeScanned: (String) -> Unit
): ImageAnalysis.Analyzer {

    private val supportedImageFormats = listOf(
        ImageFormat.YUV_420_888,
        ImageFormat.YUV_422_888,
        ImageFormat.YUV_444_888,
        )

    override fun analyze(image: ImageProxy) {
        if(image.format in supportedImageFormats) {
            val bytes = image.planes.first().buffer.toByteArray()
            //image.imageInfo.rotationDegrees
            val source = PlanarYUVLuminanceSource(
                bytes,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
            false
            )
            val binaryBmp = BinaryBitmap(HybridBinarizer(source))
            try{
                // result enthält die Information über den gescannten QR-Code, da es decodiert wurde
                val result = MultiFormatReader().apply {
                    setHints(
                        mapOf(
                           DecodeHintType.POSSIBLE_FORMATS to arrayListOf(
                               BarcodeFormat.QR_CODE
                           )
                        )
                    )
                }.decode(binaryBmp)
                onQrCodeScanned
            } catch(e: Exception){
                e.printStackTrace()
            } finally{
                image.close()
            }
        }
    }
        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()
            return ByteArray(remaining()).also {
                get(it)
            }
        }
}
