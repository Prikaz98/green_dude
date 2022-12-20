package tile

import panels.GamePanel
import java.awt.Graphics2D
import javax.imageio.ImageIO

class TileManager(gp: GamePanel) {
    val tiles: Array<Tile?> = arrayOfNulls(10)
    val tileSize = gp.tileSize

    init {
        getTitleImage()
    }

    private fun getTitleImage() {
        tiles.set(0, Tile(false, ImageIO.read(javaClass.getResourceAsStream("/texture/sand.jpg"))))
        tiles.set(1, Tile(false, ImageIO.read(javaClass.getResourceAsStream("/texture/water.png"))))
        tiles.set(2, Tile(true, ImageIO.read(javaClass.getResourceAsStream("/texture/wall.png"))))
    }

    fun draw(g2: Graphics2D) {
        g2.drawImage(tiles.get(0)?.image, 0, 0, tileSize, tileSize, null)
    }


}