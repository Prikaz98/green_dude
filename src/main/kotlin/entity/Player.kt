package entity

import handle.KeyHandler
import panels.GamePanel
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Player(
    val gp: GamePanel,
    val keyH: KeyHandler
) : Entity(4) {
    init {
        x = 100
        y = 100
        direction = "down"
        getPlayerImage()
    }

    fun getPlayerImage() {
        front_right = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front.png"))
        step1_rigth = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_1.png"))
        step2_rigth = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_2.png"))
        front_left = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_left.png"))
        step1_left = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_1_left.png"))
        step2_left = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_2_left.png"))
    }

    fun update() {
        when {
            keyH.upPressed == true -> {
                direction = "up"
                y = y?.minus(speed)
            }

            keyH.downPressed == true -> {
                direction = "down"
                y = y?.plus(speed)
            }

            keyH.leftPressed == true -> {
                direction = "left"
                x = x?.minus(speed)
            }

            keyH.rightPressed == true -> {
                direction = "right"
                x = x?.plus(speed)
            }
        }
    }

    fun draw(g2: Graphics2D) {
        var image: BufferedImage? = null
        when (direction) {
            "up" -> image = front_right
            "down" -> image = front_left
            "left" -> image = step1_left
            "right" -> image = step1_rigth
        }
        g2.drawImage(image!!, x!!, y!!, gp.tileSize, gp.tileSize, null)
    }
}