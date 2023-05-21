package entity

import utils.MovingImages
import java.awt.Graphics2D
import java.awt.Rectangle

abstract class Entity(
    var speed: Int, var direction: Direction = Direction.NOTHING, var worldX: Int = 0, var worldY: Int = 0
) {

    var spriteCounter = 0
    var spriteNum = 1
    abstract fun draw(g2: Graphics2D)
    private val solidArea = Rectangle(15, 28, 30, 30)
    fun solidArea(): Rectangle = solidArea
    private var solidAreaDefaultXY: SolidAreaDefaultXY = SolidAreaDefaultXY(solidArea().x, solidArea().y)
    fun solidAreaToDefaultParams() {
        solidArea.x = solidAreaDefaultXY.x
        solidArea.y = solidAreaDefaultXY.y
    }
}

data class SolidAreaDefaultXY(val x: Int, val y: Int)

enum class Direction {
    UP, DOWN, LEFT, RIGHT, NOTHING
}

interface Character {
    val movingImages: MovingImages
}
