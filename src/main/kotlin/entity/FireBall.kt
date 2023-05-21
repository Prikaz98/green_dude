package entity

import panels.GamePanel
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class FireBall(
    lifeTime: Long,
    var x: Int,
    var y: Int,
    val image: BufferedImage,
    val direction: Direction,
    val speed: Int,
    val gp: GamePanel
) {

    var restOfLife = lifeTime

    fun draw(g2: Graphics2D) {
        if (restOfLife > 0) {
            val player = gp.player
            val tileSize = gp.tileSize
            val screenX = x - player.worldX + player.screenX
            val screenY = y - player.worldY + player.screenY
            g2.drawImage(image, screenX, screenY, tileSize, tileSize, null)
            move()
            restOfLife--
        }
    }

    private fun move() {
        when (direction) {
            Direction.DOWN -> y += speed
            Direction.UP -> y -= speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
            else -> {}
        }
    }
}