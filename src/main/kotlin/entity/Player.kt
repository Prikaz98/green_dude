package entity

import handle.KeyHandler
import panels.GamePanel
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Player(
    val gp: GamePanel,
    val keyH: KeyHandler
) : Entity(3) {
    init {
        x = 100
        y = 100
        direction = Direction.DOWN
        getPlayerImage()
    }

    private fun getPlayerImage() {
        up1 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_up_1.png"))
        up2 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_up_2.png"))
        down1 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_down_1.png"))
        down2 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_down_2.png"))
        right1 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_1.png"))
        right2 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_2.png"))
        front_left = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_left.png"))
        left1 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_1_left.png"))
        left2 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_2_left.png"))
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
        spriteCounter++
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2
            } else if (spriteNum == 2) {
                spriteNum = 1
            }
            spriteCounter = 0
        }
    }

    fun draw(g2: Graphics2D) {
        fun evaluateImageForStep(vararg args: BufferedImage?): BufferedImage? {
            return args.get(spriteNum - 1)
        }

        var image: BufferedImage? = null
        when (direction) {
            Direction.UP -> image = evaluateImageForStep(up1, up2)
            Direction.DOWN -> image = evaluateImageForStep(down1, down2)
            Direction.LEFT -> image = evaluateImageForStep(left1, left2)
            Direction.RIGHT -> image = evaluateImageForStep(right1, right2)
            Direction.NOTHING -> image = front_left
        }
        g2.drawImage(image!!, x, y, gp.tileSize, gp.tileSize, null)
    }
}