package View
import ObserverPattern.Listener
import java.awt.Graphics
import java.awt.Panel
import java.awt.image.BufferedImage
import javax.swing.JFrame

/**
 * Created by mrneumann888@gmail.com
 */
class bmpViewer:Viewer,Listener{
    override fun update(bufferedImage: BufferedImage) {
        displayImage(bufferedImage)
    }

    override fun displayImage(bufferedImage: BufferedImage) {
        val frame = JFrame("Display image")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val panel = DrawImage(bufferedImage)
        frame.contentPane.add(panel)
        frame.setSize(bufferedImage.width, bufferedImage.height)
        frame.isVisible = true
    }
}

class DrawImage(var bufferedImage: BufferedImage): Panel() {
    override fun paint(g: Graphics) {
        g.drawImage(bufferedImage, 0, 0, this)
    }
}