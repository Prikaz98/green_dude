package utils

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object ImageLoader {
    fun getImage(path: String): BufferedImage {
        return ImageIO.read(javaClass.getResourceAsStream(path))
    }
}