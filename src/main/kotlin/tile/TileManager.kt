package tile

import entity.Player
import panels.GamePanel
import java.awt.Graphics2D
import java.io.BufferedReader
import java.io.InputStreamReader

class TileManager(gp: GamePanel) {
    val player : Player = gp.player
    data class ElementChar(val x: Int, val selectTile: Int)
    data class ElementRow(val y: Int, val cells: List<ElementChar>)

    val tiles: Array<Tile> = TileEnum.values().map { it.getTile() }.toTypedArray()
    val map = loadMap("/map")
    val tileSize = gp.tileSize


    fun draw(g2: Graphics2D) {
        map.forEach { elementRow ->
            elementRow.cells.forEach { cell ->
                val worldX = elementRow.y * tileSize
                val worldY = cell.x * tileSize
                val screenX = worldY - player.deviationX
                val screenY = worldX - player.deviationY

                g2.drawImage(
                    tiles.get(cell.selectTile)?.image,
                    screenX,
                    screenY,
                    tileSize,
                    tileSize,
                    null
                )

            }
        }
    }

    private fun loadMap(path: String): ArrayList<ElementRow> {
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