package ObserverPattern

import java.awt.image.BufferedImage

/**
 * Created by mrneumann888@gmail.com
 */

interface Listener {
    fun update(bufferedImage: BufferedImage)
}