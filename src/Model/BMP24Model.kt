package Model

import ObserverPattern.Listener
import ObserverPattern.Publisher
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

/**
* Created by mrneumann888@gmail.com
*/
class bmp24Model:Model, Publisher {
    override var listeners: LinkedList<Listener> = LinkedList<Listener>()

    override var image: BufferedImage? = null
    override val imageInfo = HashMap<String, Long>()

    override fun convertToImage(byteArray: ByteArray) {
        if (imageInfo.get("biCompression")!!.toInt() != 0){
            println("Unexpected compression")
            return
        }

        val height = imageInfo.get("biHeight")!!.toInt()
        val width = imageInfo.get("biWidth")!!.toInt()
        image = BufferedImage(width, height, 1)
        val pixelArray = byteArray.copyOfRange(imageInfo["bfOffBits"]!!.toInt(), imageInfo["bfSize"]!!.toInt())
        var index = pixelArray.size - 1
        val shift = (index + 1) / height - 3 * width

        for (i in 0..height - 1) {
            index -= shift

            for (j in width - 1 downTo 0) {
                var red = pixelArray[index--].toInt()
                if (red < 0) red += 256

                var green = pixelArray[index--].toInt()
                if (green < 0) green += 256

                var blue = pixelArray[index--].toInt()
                if (blue < 0) blue += 256

                val rgb = Color(red, green, blue).rgb

                image!!.setRGB(j, i, rgb)
            }
        }
    }
}