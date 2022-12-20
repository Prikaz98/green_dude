package entity

import java.awt.image.BufferedImage

open class Entity(
    val speed : Int,
) {

    var back : BufferedImage?=null
    var front_right : BufferedImage?=null
    var step1_right : BufferedImage?=null
    var step2_right : BufferedImage?=null
    var front_left : BufferedImage?=null
    var step1_left : BufferedImage?=null
    var step2_left : BufferedImage?=null
    var x : Int = 1
    var y : Int = 1
    var direction: Direction = Direction.NOTHING

}
enum class Direction {
    UP,DOWN,LEFT,RIGHT,NOTHING
}