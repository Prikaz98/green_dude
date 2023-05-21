package entity

import panels.GamePanel
import utils.ImageLoader
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class FireBall(
    lifeTime: Int,
    x: Int,
    y: Int,
    direction: Direction,
    speed: Int,
    val gp: GamePanel
) : Entity(speed, direction, x, y) {
    var restOfLife = lifeTime

    override fun draw(g2: Graphics2D) {
        if (restOfLife > 0) {
            val player = gp.player
            val tileSize = gp.tileSize
            val screenX = worldX - player.worldX + player.screenX
            val screenY = worldY - player.worldY + player.screenY
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
        val collisionOn = gp.collisionChecker.checkTile(this)
        if (collisionOn) {
            restOfLife = 0
        } else {
            when (direction) {
                Direction.DOWN -> worldY += speed
                Direction.UP -> worldY -= speed
                Direction.LEFT -> worldX -= speed
                Direction.RIGHT -> worldX += speed
                else -> {}
            }
        }
    }

    companion object {
        val up = ImageLoader.getImage("/objects/fireUp.png")
        val down = ImageLoader.getImage("/objects/fireDown.png")
        val left = ImageLoader.getImage("/objects/fireLeft.png")
        val right = ImageLoader.getImage("/objects/fireRight.png")
    }
}