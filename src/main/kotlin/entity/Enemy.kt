package entity

import panels.GamePanel
import utils.ImageLoader
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class Enemy(
    val hp: Int,
    x: Int,
    y: Int,
    val gp: GamePanel
) : Entity(speed = 0, Direction.DOWN, x, y) {
    override fun draw(g2: Graphics2D) {
        if (hp > 0) {
            val player = gp.player
            val tileSize = gp.tileSize
            val screenX = worldX - player.worldX + player.screenX
            val screenY = worldY - player.worldY + player.screenY
            g2.drawImage(getImage(), screenX, screenY, tileSize, tileSize, null)
        }
    }
    fun getImage(): BufferedImage = ImageLoader.getImage("/player/pixel_down_1.png")
}