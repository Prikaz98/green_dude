package entity

import java.awt.image.BufferedImage

open class Entity(
    val speed : Int,
) {

    var down1 : BufferedImage?=null
    var down2 : BufferedImage?=null
    var up1 : BufferedImage?=null
    var up2 : BufferedImage?=null
    var right1 : BufferedImage?=null
    var right2 : BufferedImage?=null
    var front_left : BufferedImage?=null
    var left1 : BufferedImage?=null
    var left2 : BufferedImage?=null
    var x : Int = 1
    var y : Int = 1
    var direction: Direction = Direction.NOTHING

    var spriteCounter = 0
    var spriteNum = 1

}
enum class Direction {
    UP,DOWN,LEFT,RIGHT,NOTHING
}