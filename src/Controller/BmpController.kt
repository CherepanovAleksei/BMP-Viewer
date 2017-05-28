package Controller

import Model.bmp24Model
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import Model.bmp8Model
import View.bmpViewer
/**
 * Created by mrneumann888@gmail.com
 */

class bmpController(private var myVeiwer:bmpViewer) :Controller{

    override fun validateFormat(path: String):Boolean {
        val byteArray= getByteArray(path) ?: return false
        if (byteArray[0].toChar() != 'B' || byteArray[1].toChar() != 'M') {
            println("It's not a BMP-structure file")
            return false
        }
        return true
    }

    override fun chooseModel(path: String) {
        val byteArray= getByteArray(path)!!
        when (getCapacity(byteArray)) {
            8 -> {
                val model = bmp8Model()
                model.addListener(myVeiwer)
                model.parseFile(byteArray)
            }
            24 -> {
                val model = bmp24Model()
                model.addListener(myVeiwer)
                model.parseFile(byteArray)
            }
        }
    }

    private fun getByteArray(path:String): ByteArray? {
        val myFile = File(path)
        val myByteArray = ByteArray(myFile.length().toInt())
        try {
            val FileInputStream = FileInputStream(myFile)
            FileInputStream.read(myByteArray)
            FileInputStream.close()
        }catch(o: FileNotFoundException){
            println(o.message)
            return null
        }
        return myByteArray
    }

    private fun getCapacity(byteArray:ByteArray):Int {
        var capacity: Int = 0
        var tmp: Int
        for (i in 0..1){
            tmp = byteArray[0x1C+i].toInt()
            if (tmp < 0) tmp += 256
            capacity += tmp.shl(i*8)
        }
        return capacity
    }
}