package tile

import panels.GamePanel
import java.awt.Graphics2D
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.imageio.ImageIO

class TileManager(gp: GamePanel) {

    data class ElementChar(val x: Int, val selectTile: Int)
    data class ElementRow(val y: Int, val cells: List<ElementChar>)

    val tiles: Array<Tile> = getTitleImage()
    val map = loadMap("/map")
    val tileSize = gp.tileSize

    private fun getTitleImage(): Array<Tile> {
        return listOf("/texture/sand.jpg", "/texture/wall.png", "/texture/water.png").map { path ->
            Tile(false, ImageIO.read(javaClass.getResourceAsStream(path)))
        }.toTypedArray()
    }

    fun draw(g2: Graphics2D) {
        map.forEach { elementRow ->
            elementRow.cells.forEach { cell ->
                g2.drawImage(
                    tiles.get(cell.selectTile)?.image,
                    cell.x * tileSize,
                    elementRow.y * tileSize,
                    tileSize,
                    tileSize,
                    null
                )
            }
        }
    }

    private fun loadMap(path : String): ArrayList<ElementRow> {
        val map = BufferedReader(InputStreamReader(javaClass.getResourceAsStream(path)))

        val mapData = map.lineSequence().foldIndexed(ArrayList<ElementRow>()) { index, acc, row ->
            val chars =
                row.toCharArray().asSequence().foldIndexed(ArrayList<ElementChar>(), { index, accChar, selectTile ->
                    accChar.add(ElementChar(index, selectTile.digitToInt()))
                    accChar
                })
            acc.add(ElementRow(index, chars))
            acc
        }
        return mapData
    }
}