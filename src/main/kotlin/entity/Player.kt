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
        direction = Direction.DOWN
        getPlayerImage()
    }

    private fun getPlayerImage() {
        back = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_back.png"))
        front_right = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front.png"))
        step1_right = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_1.png"))
//        step2_right = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_2.png"))
        front_left = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_left.png"))
        step1_left = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_1_left.png"))
//        step2_left = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_2_left.png"))
    }

    fun update() {
        when {
            keyH.upPressed -> {
                direction = Direction.UP
                y -= speed
            }
            keyH.downPressed -> {
                direction = Direction.DOWN
                y += speed
            }
            keyH.leftPressed -> {
                direction = Direction.LEFT
                x -= speed
            }
            keyH.rightPressed -> {
                direction = Direction.RIGHT
                x += speed
            }
        }
    }

    fun draw(g2: Graphics2D) {
        var image: BufferedImage? = null
        when (direction) {
            Direction.UP -> image = back
            Direction.DOWN -> image = front_left
            Direction.LEFT -> image = step1_left
            Direction.RIGHT -> image = step1_right
            Direction.NOTHING -> image = front_right
        }
        g2.drawImage(image!!, x, y, gp.tileSize, gp.tileSize, null)
    }
}