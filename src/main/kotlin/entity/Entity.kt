package entity

import handle.KeyHandler
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage

abstract class Entity(
    var speed: Int,
) {
    var worldX: Int = 0
    var worldY: Int = 0
    var down1: BufferedImage? = null
    var down2: BufferedImage? = null
    var up1: BufferedImage? = null
    var up2: BufferedImage? = null
    var right1: BufferedImage? = null
    var right2: BufferedImage? = null
    var left1: BufferedImage? = null
    var left2: BufferedImage? = null
    var direction: Direction = Direction.NOTHING

    var spriteCounter = 0
    var spriteNum = 1
    abstract fun draw(g2: Graphics2D)
    abstract fun solidArea(): Rectangle
    abstract fun update(keyHandler: KeyHandler)
    abstract fun getSolidAreaDefaultXY() : SolidAreaDefaultXY
    abstract fun solidAreaToDefaultParams()
}
data class SolidAreaDefaultXY(val x : Int, val y : Int)

enum class Direction {
    UP, DOWN, LEFT, RIGHT, NOTHING
}