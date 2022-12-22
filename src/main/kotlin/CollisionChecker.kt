import entity.Direction.*
import entity.Entity
import panels.GamePanel
import tile.getByXY

class CollisionChecker(gp: GamePanel) {
    val gp = gp
    fun checkTile(entity: Entity): Boolean {

        val entityLeftWorldX = entity.worldX + entity.solidArea().x
        val entityRightWorldX = entity.worldX + entity.solidArea().x + entity.solidArea().width
        val entityTopWorldY = entity.worldY + entity.solidArea().y
        val entityBottomWorldY = entity.worldY + entity.solidArea().y + entity.solidArea().height

        val entityLeftCol = entityLeftWorldX / gp.tileSize
        val entityRightCol = entityRightWorldX / gp.tileSize
        val entityTopRow = entityTopWorldY / gp.tileSize
        val entityBottomRow = entityBottomWorldY / gp.tileSize

        when (entity.direction) {
            UP -> {
                val entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize
                val tileNum1 = gp.tileManager.mapTileNumber.getByXY(entityLeftCol,entityTopRow).selectTile
                val tileNum2 = gp.tileManager.mapTileNumber.getByXY(entityRightCol,entityTopRow).selectTile
                if(gp.tileManager.tiles.get(tileNum1)?.collision == true ||
                        gp.tileManager.tiles.get(tileNum2)?.collision == true){
                   return true
                }
            }

            DOWN -> {
                val entityBottomRow = (entityBottomWorldY - entity.speed)/gp.tileSize
                val tileNum1 = gp.tileManager.mapTileNumber.getByXY(entityLeftCol,entityBottomRow).selectTile
                val tileNum2 = gp.tileManager.mapTileNumber.getByXY(entityRightCol,entityBottomRow).selectTile
                if(gp.tileManager.tiles.get(tileNum1)?.collision == true ||
                    gp.tileManager.tiles.get(tileNum2)?.collision == true){
                    return true
                }
            }

            LEFT -> {
                val entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize
                val tileNum1 = gp.tileManager.mapTileNumber.getByXY(entityLeftCol,entityBottomRow).selectTile
                val tileNum2 = gp.tileManager.mapTileNumber.getByXY(entityLeftCol,entityTopRow).selectTile
                if(gp.tileManager.tiles.get(tileNum1)?.collision == true ||
                    gp.tileManager.tiles.get(tileNum2)?.collision == true){
                    return true
                }
            }

            RIGHT -> {
                val entityRightCol = (entityRightWorldX - entity.speed)/gp.tileSize
                val tileNum1 = gp.tileManager.mapTileNumber.getByXY(entityRightCol,entityBottomRow).selectTile
                val tileNum2 = gp.tileManager.mapTileNumber.getByXY(entityRightCol,entityTopRow).selectTile
                if(gp.tileManager.tiles.get(tileNum1)?.collision == true ||
                    gp.tileManager.tiles.get(tileNum2)?.collision == true){
                    return true
                }
            }

            NOTHING -> {
                return false;
            }
        }

        return false;
    }
}