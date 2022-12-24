package objects

import panels.GamePanel
import java.awt.Graphics2D
import java.awt.image.BufferedImage


abstract class SuperObject {
    var image : BufferedImage? = null
    var name : String? = null
    var collision : Boolean = false
    var worldX : Int = 0
    var worldY : Int = 0

    fun draw(g2 : Graphics2D, gp: GamePanel){
        val player = gp.player
        val tileSize = gp.tileSize
        val screenX = worldX - player.worldX + player.screenX
        val screenY = worldY - player.worldY + player.screenY

        if (worldX + tileSize > player.worldX - player.screenX
            && worldX - tileSize < player.worldX + player.screenX
            && worldY + tileSize > player.worldY - player.screenY
            && worldY - tileSize < player.worldY + player.screenY
        ) {
            g2.drawImage(
                image!!,
                screenX,
                screenY,
                tileSize,
                tileSize,
                null
            )
        }
    }
}