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
        front = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front.png"))
        step1 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_1.png"))
        step2 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_2.png"))
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
            "up" -> image = front
            "down" -> image = front
            "left" -> image = step1
            "right" -> image = step2
        }
        g2.drawImage(image!!, x!!, y!!, gp.tileSize, gp.tileSize, null)
    }
}