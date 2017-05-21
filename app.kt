/**
 * Created by mrneumann888@gmail.com
 */
import Controller.bmpController
import View.bmpViewer

fun main(args:Array<String>) {
    while (true) {
        val path = readLine()!!
        if (path.endsWith(".bmp") || path.endsWith(".dib") || path.endsWith(".rle")) {
            val myViewer = bmpViewer()
            val myController = bmpController(myViewer)
            if (myController.validateFormat(path)) myController.chooseModel(path)
        } else {
            println("It's not a BMP file. Specify the correct path to file!")
        }
    }
}