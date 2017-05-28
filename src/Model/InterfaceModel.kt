package Model

import ObserverPattern.Publisher
import java.awt.image.BufferedImage

/**
 * Created by mrneumann888@gmail.com
 */

interface Model: Publisher {
    var image: BufferedImage?
    val imageInfo : HashMap<String,Long>

    fun parseFile(byteArray: ByteArray){
        if(getData(byteArray,0x0E,4).toInt() == 40) getInfo(byteArray, imageInfo) //3 version
        else {
            println("Unexpected bitmap information header(BMP-version)")
            return
        }
        convertToImage(byteArray)
        notifyListeners()
    }

    fun convertToImage(byteArray: ByteArray)

    fun getInfo(byteArray: ByteArray, imageInfo: HashMap<String, Long>){
        imageInfo.put("bfType", getData(byteArray, 0x00, 2))
        imageInfo.put("bfSize", getData(byteArray, 0x02, 4))
        imageInfo.put("bfOffBits", getData(byteArray, 0x0A, 4))
        imageInfo.put("biSize", getData(byteArray, 0x0E, 4))
        imageInfo.put("biWidth", getData(byteArray, 0x12, 4))
        imageInfo.put("biHeight", getData(byteArray, 0x16, 4))
        imageInfo.put("biPlanes", getData(byteArray, 0x1A, 2))
        imageInfo.put("biBitCount", getData(byteArray, 0x1C, 2))
        imageInfo.put("biCompression", getData(byteArray, 0x1E, 2))
        imageInfo.put("biSizeImage", getData(byteArray, 0x22, 4))
        imageInfo.put("biXPelsPerMeter", getData(byteArray, 0x26, 4))
        imageInfo.put("biYPelsPerMeter", getData(byteArray, 0x2A, 4))
        imageInfo.put("biClrUsed", getData(byteArray, 0x2E, 4))
        imageInfo.put("biClrImportant", getData(byteArray, 0x32, 4))
    }

    fun getData(byteArray: ByteArray, index: Int, length:Int): Long{
        var data: Long = 0
        var tmp: Long
        for (i in 0..length-1){
            tmp = byteArray[index+i].toLong()
            if (tmp < 0) tmp += 256
            data += tmp.shl(i*8)
        }
        return data
    }

    override fun notifyListeners() {
        for (obs in listeners) obs.update(image!!)
    }
}