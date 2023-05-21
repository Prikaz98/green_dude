package entity

import handle.KeyHandler
import objects.ObjBoots
import objects.ObjBox
import objects.ObjDoor
import objects.ObjKey
import panels.GamePanel
import utils.ImageLoader
import utils.MovingImages
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Player(
    val gp: GamePanel,
    override val movingImages: MovingImages
) : Character, Entity(4) {

    var screenX: Int = gp.screenWidth / 2 - (gp.tileSize / 2)
    var screenY: Int = gp.screenHeight / 2 - (gp.tileSize / 2)
    var hasKeys: Int = 0
    val keyImg = ImageLoader.getImage("/objects/key.png")
    val fireBalls = ArrayList<FireBall>()
    val notificationList = ArrayList<TextNotification>()

    init {
        setDefaultValues()
    }

    private fun setDefaultValues() {
        worldX = gp.tileSize * 7
        worldY = gp.tileSize * 24
        direction = Direction.DOWN
    }


    private fun KeyHandler.setActionOnPressed(
        onUp: () -> Unit, onDown: () -> Unit, onLeft: () -> Unit, onRight: () -> Unit, onFire: () -> Unit = {}
    ) {
        when {
            this.upPressed -> onUp()
            this.downPressed -> onDown()
            this.leftPressed -> onLeft()
            this.rightPressed -> onRight()
            this.firePressed -> onFire()
        }
    }

    fun update(keyHandler: KeyHandler) {
        keyHandler.setActionOnPressed(
            onUp = { direction = Direction.UP },
            onDown = { direction = Direction.DOWN },
            onLeft = { direction = Direction.LEFT },
            onRight = { direction = Direction.RIGHT },
            onFire = throwFireBall()
        )

        var collisionOn = gp.collisionChecker.checkTile(this)
        val checkObjectInfo = gp.collisionChecker.checkObject(this, true)

        checkObjectInfo?.also {
            if (it.collision) {
                collisionOn = it.collision
            }
        }

        checkObjectInfo?.indexOfObject?.also { pickUpObject(it) }

        if (!collisionOn) {
            keyHandler.setActionOnPressed(onUp = { worldY -= speed },
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

    private fun throwFireBall() = {
        val lifeTime = 100
        val threshold = 20
        if (fireBalls.find { it.restOfLife > (lifeTime - threshold) } == null) {
            fireBalls.add(FireBall(lifeTime, worldX, worldY, direction, 8, gp))
        }
    }

    class TextNotification(val text: String, lifeTime: Long) : Entity(1, Direction.NOTHING, 50, 300) {
        var restOfLife = lifeTime
        override fun draw(g2: Graphics2D) {
            if (restOfLife > 0) {
                worldY -= 1
                g2.drawString(text, worldX, worldY)
                restOfLife--
            }
        }
    }


    fun pickUpObject(indexObj: Int) {
        gp.arrObj.get(indexObj)?.let { obj ->
            when (obj) {
                is ObjKey -> {
                    hasKeys++
                    notificationList.add(TextNotification("GOT A NEW KEY!", 80))
                    gp.arrObj[indexObj] = null
                }

                is ObjDoor -> {
                    if (hasKeys > 0) {
                        notificationList.add(TextNotification("OPENED THE DOOR!", 80))
                        gp.arrObj[indexObj] = null
                        hasKeys--
                    }
                }

                is ObjBoots -> {
                    speed++
                    notificationList.add(TextNotification("FOUND THE SOCKS!", 80))
                    gp.arrObj[indexObj] = null
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


    var start = LocalDateTime.now().withMinute(0).withSecond(0)

    override fun draw(g2: Graphics2D) {
        fun evaluateImageForStep(vararg args: BufferedImage?): BufferedImage? {
            return args.get(spriteNum - 1)
        }

        val dudeImg = when (direction) {
            Direction.UP -> evaluateImageForStep(movingImages.up1, movingImages.up2)
            Direction.DOWN -> evaluateImageForStep(movingImages.down1, movingImages.down2)
            Direction.LEFT -> evaluateImageForStep(movingImages.left1, movingImages.left2)
            Direction.RIGHT -> evaluateImageForStep(movingImages.right1, movingImages.right2)
            Direction.NOTHING -> movingImages.down1
        }

        drawKeysInfo(g2)
        drawNotification(g2)
        drawFireBalls(g2)

        start = start.plusNanos(16 * 1_000_000)//0.016 seconds
        g2.drawString(start.format(DateTimeFormatter.ofPattern("mm:ss:S")), 300, 50)

        g2.drawString("For run use arrows, Button X for Fire", 20, 750)

        g2.drawImage(dudeImg, screenX, screenY, gp.tileSize, gp.tileSize, null)
    }

    private fun drawFireBalls(g2: Graphics2D) {
        fireBalls.removeIf { it.restOfLife < 0 }
        fireBalls.forEach { it.draw(g2) }
    }

    private fun drawNotification(g2: Graphics2D) {
        notificationList.removeIf { it.restOfLife < 0 }
        notificationList.forEach { it.draw(g2) }
    }

    private fun drawKeysInfo(g2: Graphics2D) {
        g2.drawImage(keyImg, 15, 15, gp.tileSize - 10, gp.tileSize - 10, null)
        g2.drawString("KEYS - $hasKeys", 65, 50)
    }

}