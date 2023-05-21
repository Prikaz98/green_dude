package entity

import panels.GamePanel
import utils.ImageLoader
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class FireBall(
    lifeTime: Int,
    var x: Int,
    var y: Int,
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
            g2.drawImage(getImage(), screenX, screenY, tileSize, tileSize, null)
            move()
            restOfLife--
        }
    }

    private fun getImage(): BufferedImage =
        when (direction) {
            Direction.DOWN -> down
            Direction.UP -> up
            Direction.LEFT -> left
            Direction.RIGHT -> right
            else -> up
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

    companion object {
        val up = ImageLoader.getImage("/objects/fireUp.png")
        val down = ImageLoader.getImage("/objects/fireDown.png")
        val left = ImageLoader.getImage("/objects/fireLeft.png")
        val right = ImageLoader.getImage("/objects/fireRight.png")
    }
}