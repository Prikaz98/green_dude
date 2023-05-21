package entity

import handle.KeyHandler
import objects.ObjBoots
import objects.ObjBox
import objects.ObjDoor
import objects.ObjKey
import other.Quest
import panels.GamePanel
import utils.ImageLoader
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Player(
    val gp: GamePanel
) : Entity(4) {
    var screenX: Int = gp.screenWidth / 2 - (gp.tileSize / 2)
    var screenY: Int = gp.screenHeight / 2 - (gp.tileSize / 2)
    var hasKeys: Int = 0
    val keyImg = ImageLoader.getImage("/objects/key.png")

    var quests = arrayListOf(
        Quest("find two keys on the map", { hasKeys == 2 }),
        Quest("find socks and become faster", { speed > 4 })
    )

    init {
        setDefaultValues()
        getPlayerImage()
    }

    private var solidArea: Rectangle = Rectangle(15, 28, 28, 28)
    override fun solidArea(): Rectangle = solidArea

    private var solidAreaDefaultXY: SolidAreaDefaultXY = SolidAreaDefaultXY(solidArea.x, solidArea.y)
    override fun getSolidAreaDefaultXY(): SolidAreaDefaultXY {
        return solidAreaDefaultXY
    }

    override fun solidAreaToDefaultParams() {
        solidArea.x = solidAreaDefaultXY.x
        solidArea.y = solidAreaDefaultXY.y
    }

    private fun setDefaultValues() {
        worldX = gp.tileSize * 7
        worldY = gp.tileSize * 24
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

    val fireBalls = ArrayList<FireBall>()

    private fun KeyHandler.setActionOnPressed(
        onUp: () -> Unit, onDown: () -> Unit, onLeft: () -> Unit, onRight: () -> Unit,
        onFire: () -> Unit = {}
    ) {
        when {
            this.upPressed -> onUp()
            this.downPressed -> onDown()
            this.leftPressed -> onLeft()
            this.rightPressed -> onRight()
            this.firePressed -> onFire()
        }
    }

    override fun update(keyHandler: KeyHandler) {
        keyHandler.setActionOnPressed(
            onUp = { direction = Direction.UP },
            onDown = { direction = Direction.DOWN },
            onLeft = { direction = Direction.LEFT },
            onRight = { direction = Direction.RIGHT },
            onFire = {
                fireBalls.add(FireBall(100, worldX, worldY, keyImg,direction, 4, gp))
            })

        var collisionOn = gp.collisionChecker.checkTile(this)
        val checkObjectInfo = gp.collisionChecker.checkObject(this, true)

        checkObjectInfo?.also {
            if (it.collision) {
                collisionOn = it.collision
            }
        }

        checkObjectInfo?.indexOfObject?.also { pickUpObject(it) }

        if (!collisionOn) {
            keyHandler.setActionOnPressed(
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

    class TextNotification(val text: String, lifeTime: Long) {
        var restOfLife = lifeTime
        var y = 300
        fun draw(g2: Graphics2D) {
            if (restOfLife > 0) {
                y = y - 1
                g2.drawString(text, 50, y)
                restOfLife--
            }
        }
    }

    val notificationList = ArrayList<TextNotification>()

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
            Direction.UP -> evaluateImageForStep(up1, up2)
            Direction.DOWN -> evaluateImageForStep(down1, down2)
            Direction.LEFT -> evaluateImageForStep(left1, left2)
            Direction.RIGHT -> evaluateImageForStep(right1, right2)
            Direction.NOTHING -> down1
        }

        drawKeysInfo(g2)
        drawNotification(g2)
        drawFireBalls(g2)

        start = start.plusNanos(16 * 1_000_000)//0.016 seconds
        g2.drawString(start.format(DateTimeFormatter.ofPattern("mm:ss:S")), 300, 50)
        g2.drawImage(dudeImg, screenX, screenY, gp.tileSize, gp.tileSize, null)
    }

    private fun drawFireBalls(g2: Graphics2D) {
        fireBalls.removeIf { it.restOfLife < 0 }
        fireBalls.forEach {
            it.draw(g2)
        }
    }

    private fun drawNotification(g2: Graphics2D) {
        notificationList.removeIf { it.restOfLife < 0 }
        notificationList.forEach { it.draw(g2) }
    }

    private fun drawKeysInfo(g2: Graphics2D) {
        g2.drawImage(keyImg, 15, 15, gp.tileSize - 10, gp.tileSize - 10, null)
        g2.drawString("KEYS - $hasKeys", 65, 50)
    }

    fun getInfo(): String {
        val builder = StringBuilder()
        builder.append("DUDE INFO\n   keys : $hasKeys\n   speed : $speed\n")
        builder.append("QUESTS : \n${quests.mapIndexed { i, q -> "${i + 1}. ${q.info()}" }.joinToString("\n")}")
        return builder.toString()
    }
}