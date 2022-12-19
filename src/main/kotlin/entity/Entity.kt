package entity

import java.awt.image.BufferedImage

open class Entity(
    val speed : Int
) {
    var x : Int? = null
    var y : Int? = null
    var front : BufferedImage?=null
    var step1 : BufferedImage?=null
    var step2 : BufferedImage?=null
    var direction: String?= null
}