import Model.bmp24Model
import Model.bmp8Model
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.awt.Color
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.imageio.ImageIO.read

/**
 * Created by mrneumann888@gmail.com
 */
internal class bmpViewerTest{
    @Test
    fun img8bit(){
        val testModel = bmp8Model()
        val name = "/home/mrneumann/IdeaProjects/Parser/src/bmp/freebsd2_8bit.bmp"
        val file = File(name)
        val actualImage = read(file)
        val path: Path = Paths.get(name)
        testModel.parseFile(Files.readAllBytes(path))
        val width = actualImage.width
        val height = actualImage.height
        for (i in 0..width - 1){
            for (j in 0..height - 1){
                assertEquals(actualImage.getRGB(i, j), testModel.image!!.getRGB(i, j))
            }
        }

    }

    @Test
    fun img24bit(){
        val testModel = bmp24Model()
        val name = "//home/mrneumann/IdeaProjects/Parser/src/bmp/dodj_24bit.bmp"
        val file = File(name)
        val actualImage = read(file)
        val path: Path = Paths.get(name)
        testModel.parseFile(Files.readAllBytes(path))
        val width = actualImage.width
        val height = actualImage.height
        for (i in 0..width - 1){
            for (j in 0..height - 1){
                assertEquals(actualImage.getRGB(i, j), testModel.image!!.getRGB(i, j))
            }
        }
    }
}