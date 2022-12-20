package entity

import java.awt.image.BufferedImage

open class Entity(
    val speed : Int
) {
    var x : Int? = null
    var y : Int? = null
    var front_right : BufferedImage?=null
    var step1_rigth : BufferedImage?=null
    var step2_rigth : BufferedImage?=null
    var front_left : BufferedImage?=null
    var step1_left : BufferedImage?=null
    var step2_left : BufferedImage?=null
    var direction: String?= null
}