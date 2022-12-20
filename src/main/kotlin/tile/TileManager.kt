package tile

import panels.GamePanel
import java.awt.Graphics2D
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.imageio.ImageIO

class TileManager(gp: GamePanel) {
    val tiles: Array<Tile?> = arrayOfNulls(10)
    val tileSize = gp.tileSize

    init {
        getTitleImage()
    }

    private fun getTitleImage() {
        tiles.set(0, Tile(false, ImageIO.read(javaClass.getResourceAsStream("/texture/sand.jpg"))))
        tiles.set(1, Tile(true, ImageIO.read(javaClass.getResourceAsStream("/texture/wall.png"))))
        tiles.set(2, Tile(false, ImageIO.read(javaClass.getResourceAsStream("/texture/water.png"))))
    }

    fun draw(g2: Graphics2D) {
        val map = BufferedReader(InputStreamReader(javaClass.getResourceAsStream("/map")))

        data class ElementChar(val x: Int, val selectTile: Int)
        data class ElementRow(val y: Int, val cells: List<ElementChar>)
        val mapData = map.lineSequence().foldIndexed(ArrayList<ElementRow>()) { index, acc, row ->
            val chars = row.toCharArray().asSequence().foldIndexed(ArrayList<ElementChar>(), { index, accChar, selectTile ->
                accChar.add(ElementChar(index, selectTile.digitToInt()))
                accChar
            })
            acc.add(ElementRow(index, chars))
            acc
        }
        mapData.forEach{ elementRow ->
            elementRow.cells.forEach{ cell ->
                g2.drawImage(tiles.get(cell.selectTile)?.image, cell.x * tileSize, elementRow.y * tileSize, tileSize, tileSize, null)
            }
        }

    }
}