package utils

import java.awt.image.BufferedImage


class MovingImagesFactory {
    fun getForMainCharacter(): MovingImages =
        MovingImages(
            down1 = ImageLoader.getImage("/player/pixel_down_1.png"),
            down2 = ImageLoader.getImage("/player/pixel_down_2.png"),
            up1 = ImageLoader.getImage("/player/pixel_up_1.png"),
            up2 = ImageLoader.getImage("/player/pixel_up_2.png"),
            right1 = ImageLoader.getImage("/player/pixel_front_step_1.png"),
            right2 = ImageLoader.getImage("/player/pixel_front_step_2.png"),
            left1 = ImageLoader.getImage("/player/pixel_front_step_1_left.png"),
            left2 = ImageLoader.getImage("/player/pixel_front_step_2_left.png")
        )
}

data class MovingImages(
    val down1: BufferedImage,
    val down2: BufferedImage,
    val up1: BufferedImage,
    val up2: BufferedImage,
    val right1: BufferedImage,
    val right2: BufferedImage,
    val left1: BufferedImage,
    val left2: BufferedImage,
)