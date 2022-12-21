package entity

import handle.KeyHandler
import panels.GamePanel
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Player(
    val gp: GamePanel
) : Entity(4) {

    var screenX: Int = gp.screenWidth / 2 - (gp.tileSize / 2)
    var screenY: Int = gp.screenHeight / 2 - (gp.tileSize / 2)

    private var solidArea: Rectangle? = null

    override fun solidArea(): Rectangle {
        return if (solidArea != null) {
            solidArea!!
        } else {

            solidArea = Rectangle(0, 0, gp.tileSize - 10, gp.tileSize - 10)
            solidArea!!;
        }
    }

    override fun init(): Player {
        setDefaultValues()
        getPlayerImage()
        return this
    }

    private fun setDefaultValues() {
        worldX = gp.tileSize * 14
        worldY = gp.tileSize * 11
        direction = Direction.DOWN
    }

    private fun getPlayerImage() {
        up1 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_up_1.png"))
        up2 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_up_2.png"))
        down1 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_down_1.png"))
        down2 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_down_2.png"))
        right1 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_1.png"))
        right2 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_2.png"))
        left1 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_1_left.png"))
        left2 = ImageIO.read(javaClass.getResourceAsStream("/player/pixel_front_step_2_left.png"))
    }

    private fun KeyHandler.setActionOnPressed(
        onUp: () -> Unit, onDown: () -> Unit, onLeft: () -> Unit, onRight: () -> Unit
    ) {
        when {
            this.upPressed -> {
                onUp()
            }

            this.downPressed -> {
                onDown()
            }

            this.leftPressed -> {
                onLeft()
            }

            this.rightPressed -> {
                onRight()
            }
        }

    }

    override fun update(keyH: KeyHandler) {
        keyH.setActionOnPressed(
            onUp = { direction = Direction.UP },
            onDown = { direction = Direction.DOWN },
            onLeft = { direction = Direction.LEFT },
            onRight = { direction = Direction.RIGHT })

        collisionOn = gp.collisionChecker.checkTile(this)
        if (!collisionOn) {
            keyH.setActionOnPressed(
                onUp = { worldY -= speed },
                onDown = { worldY += speed },
                onLeft = { worldX -= speed },
                onRight = { worldX += speed })
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


    override fun draw(g2: Graphics2D) {
        fun evaluateImageForStep(vararg args: BufferedImage?): BufferedImage? {
            return args.get(spriteNum - 1)
        }

        var image: BufferedImage? = null
        when (direction) {
            Direction.UP -> image = evaluateImageForStep(up1, up2)
            Direction.DOWN -> image = evaluateImageForStep(down1, down2)
            Direction.LEFT -> image = evaluateImageForStep(left1, left2)
            Direction.RIGHT -> image = evaluateImageForStep(right1, right2)
            Direction.NOTHING -> image = down1
        }
        g2.drawImage(image!!, screenX!!, screenY!!, gp.tileSize, gp.tileSize, null)
    }

}