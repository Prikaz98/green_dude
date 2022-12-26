package tile

import entity.Player
import panels.GamePanel
import java.awt.Graphics2D
import java.io.BufferedReader
import java.io.InputStreamReader

fun ArrayList<TileManager.ElementRow>.getByXY(x: Int, y: Int): TileManager.ElementCell {
    return this.get(y).cells.get(x)
}

class TileManager(gp: GamePanel) {
    val player: Player = gp.player

    data class ElementRow(val y: Int, val cells: List<ElementCell>)
    data class ElementCell(val x: Int, val selectTile: Int)

    val tiles: Array<Tile> = TileEnum.values().map { it.getTile() }.toTypedArray()
    val mapTileNumber = loadMap("/map")
    val tileSize = gp.tileSize


    fun draw(g2: Graphics2D) {
        mapTileNumber.forEach { elementRow ->
            elementRow.cells.forEach { cell ->
                val worldX = cell.x * tileSize
                val worldY = elementRow.y * tileSize
                val screenX = worldX - player.worldX + player.screenX
                val screenY = worldY - player.worldY + player.screenY

                if (worldX + tileSize > player.worldX - player.screenX
                    && worldX - tileSize < player.worldX + player.screenX
                    && worldY + tileSize > player.worldY - player.screenY
                    && worldY - tileSize < player.worldY + player.screenY
                ) {
                    g2.drawImage(
                        tiles.get(cell.selectTile).image,
                        screenX,
                        screenY,
                        tileSize,
                        tileSize,
                        null
                    )
                }
            }
        }
    }


    private fun loadMap(path: String): ArrayList<ElementRow> {
        val map = BufferedReader(InputStreamReader(javaClass.getResourceAsStream(path)))

        return map.lineSequence().foldIndexed(ArrayList()) { indexRow, acc, row ->
            val chars =
                row.toCharArray().asSequence().foldIndexed(ArrayList<ElementCell>(), { indexCell, accChar, selectTile ->
                    accChar.add(ElementCell(indexCell, selectTile.digitToInt()))
                    accChar
                })
            acc.add(ElementRow(indexRow, chars))
            acc
        }
    }
}