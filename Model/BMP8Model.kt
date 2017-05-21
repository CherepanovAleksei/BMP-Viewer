package Model

import ObserverPattern.Listener
import ObserverPattern.Publisher
import com.intellij.designer.clipboard.SimpleTransferable.getData
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

/**
 * Created by mrneumann888@gmail.com
 */
class bmp8Model:Model{
    override var listeners: LinkedList<Listener> = LinkedList<Listener>()
    override var image: BufferedImage? = null
    override val imageInfo = HashMap<String, Long>()

    override fun convertToImage(byteArray: ByteArray) {
        if (imageInfo.get("biClrUsed")!!.toInt() == 0){ //Размер таблицы цветов в ячейках.
            val tmp: Int = 1
            imageInfo["biClrUsed"] = tmp.shl(imageInfo.get("biBitCount")!!.toInt()).toLong()
        }
        if (imageInfo.get("biCompression")!!.equals(1)){
            println("Unexpected compression")
            return
        }

        val colorTable = byteArray.copyOfRange(0x36, imageInfo["bfOffBits"]!!.toInt() - 1)
        val pixelArray = byteArray.copyOfRange(imageInfo["bfOffBits"]!!.toInt(), imageInfo["bfSize"]!!.toInt())
        val height = imageInfo["biHeight"]!!.toInt()
        val width = imageInfo["biWidth"]!!.toInt()

        image = BufferedImage(width, height, 1)
        var index = pixelArray.size - 1
        val shift = (index + 1) / height - width

        for (i in 0..height - 1) {
            index -= shift

            for (j in width - 1 downTo 0) {
                var pix = pixelArray[index--].toInt()
                if (pix < 0) pix += 256
                pix = pix shl 2

                var blue = colorTable[pix++].toInt()
                if (blue < 0) blue += 256

                var green = colorTable[pix++].toInt()
                if (green < 0) green += 256

                var red = colorTable[pix].toInt()
                if (red < 0) red += 256

                val rgb = Color(red, green, blue).rgb

                image!!.setRGB(j, i, rgb)
            }
        }
    }
}