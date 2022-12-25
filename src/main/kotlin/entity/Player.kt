package entity

import handle.KeyHandler
import objects.ObjBox
import objects.ObjDoor
import objects.ObjKey
import panels.GamePanel
import utils.ImageLoader
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage

class Player(
    val gp: GamePanel
) : Entity(4) {
    var screenX: Int = gp.screenWidth / 2 - (gp.tileSize / 2)
    var screenY: Int = gp.screenHeight / 2 - (gp.tileSize / 2)
    var hasKeys: Int = 0


    init {
        setDefaultValues()
        getPlayerImage()
    }

    private var solidArea: Rectangle = Rectangle(15, 28, 28, 28)
    override fun solidArea(): Rectangle {
        return solidArea
    }

    private var solidAreaDefaultXY: SolidAreaDefaultXY = SolidAreaDefaultXY(solidArea.x, solidArea.y)
    override fun getSolidAreaDefaultXY(): SolidAreaDefaultXY {
        return solidAreaDefaultXY
    }

    override fun solidAreaToDefaultParams() {
        solidArea.x = solidAreaDefaultXY.x
        solidArea.y = solidAreaDefaultXY.y
    }

    private fun setDefaultValues() {
        worldX = gp.tileSize * 31
        worldY = gp.tileSize * 15
        direction = Direction.DOWN
    }

    private fun getPlayerImage() {
        up1 = ImageLoader.getImage("/player/pixel_up_1.png")
        up2 = ImageLoader.getImage("/player/pixel_up_2.png")
        down1 = ImageLoader.getImage("/player/pixel_down_1.png")
        down2 = ImageLoader.getImage("/player/pixel_down_2.png")
        right1 = ImageLoader.getImage("/player/pixel_front_step_1.png")
        right2 = ImageLoader.getImage("/player/pixel_front_step_2.png")
        left1 = ImageLoader.getImage("/player/pixel_front_step_1_left.png")
        left2 = ImageLoader.getImage("/player/pixel_front_step_2_left.png")
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

        var collisionOn = gp.collisionChecker.checkTile(this)
        val checkObjectInfo = gp.collisionChecker.checkObject(this, true)

        checkObjectInfo?.also {
            if (it.collision) {
                collisionOn = it.collision
            }
        }

        checkObjectInfo?.indexOfObject?.also { pickUpObject(it) }

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

    fun pickUpObject(indexObj: Int) {
        gp.arrObj.get(indexObj)?.let { obj ->
            when (obj) {
                is ObjKey -> {
                    hasKeys++
                    gp.arrObj[indexObj] = null
                }

                is ObjDoor -> {
                    if (hasKeys > 0) {
                        gp.arrObj[indexObj] = null
                        hasKeys--
                    }
                }

                is ObjBox -> {
//                    if(hasKey){
//                        gp.arrObj[indexObj]?.isOpen = false
//                        hasKey = false
//                    }
                }
            }
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