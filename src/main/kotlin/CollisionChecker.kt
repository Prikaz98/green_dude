import entity.Direction.*
import entity.Entity
import objects.SuperObject
import panels.GamePanel
import tile.getByXY

class CollisionChecker(gp: GamePanel) {
    val gp = gp
    fun checkTile(entity: Entity): Boolean {

        val entityLeftWorldX = entity.worldX + entity.solidArea().x
        val entityRightWorldX = entity.worldX + entity.solidArea().x + entity.solidArea().width
        val entityTopWorldY = entity.worldY + entity.solidArea().y
        val entityBottomWorldY = entity.worldY + entity.solidArea().y + entity.solidArea().height

        var entityLeftCol = entityLeftWorldX / gp.tileSize
        var entityRightCol = entityRightWorldX / gp.tileSize
        var entityTopRow = entityTopWorldY / gp.tileSize
        var entityBottomRow = entityBottomWorldY / gp.tileSize

        when (entity.direction) {
            UP -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize
                val tileNum1 = gp.tileManager.mapTileNumber.getByXY(entityLeftCol, entityTopRow).selectTile
                val tileNum2 = gp.tileManager.mapTileNumber.getByXY(entityRightCol, entityTopRow).selectTile
                return (gp.tileManager.tiles.get(tileNum1).collision ||
                        gp.tileManager.tiles.get(tileNum2).collision)
            }

            DOWN -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize
                val tileNum1 = gp.tileManager.mapTileNumber.getByXY(entityLeftCol, entityBottomRow).selectTile
                val tileNum2 = gp.tileManager.mapTileNumber.getByXY(entityRightCol, entityBottomRow).selectTile
                return (gp.tileManager.tiles.get(tileNum1).collision ||
                        gp.tileManager.tiles.get(tileNum2).collision)
            }

            RIGHT -> {
                entityRightCol = (entityRightWorldX - entity.speed) / gp.tileSize
                val tileNum1 = gp.tileManager.mapTileNumber.getByXY(entityRightCol, entityBottomRow).selectTile
                val tileNum2 = gp.tileManager.mapTileNumber.getByXY(entityRightCol, entityTopRow).selectTile
                return (gp.tileManager.tiles.get(tileNum1).collision ||
                        gp.tileManager.tiles.get(tileNum2).collision)
            }

            LEFT -> {
                entityLeftCol = (entityLeftWorldX + entity.speed) / gp.tileSize
                val tileNum1 = gp.tileManager.mapTileNumber.getByXY(entityLeftCol, entityBottomRow).selectTile
                val tileNum2 = gp.tileManager.mapTileNumber.getByXY(entityLeftCol, entityTopRow).selectTile
                return (gp.tileManager.tiles.get(tileNum1).collision ||
                        gp.tileManager.tiles.get(tileNum2).collision)
            }


            NOTHING -> {
                return false;
            }
        }

        return false;
    }

    data class CheckObjectInfo(val collision: Boolean, val indexOfObject: Int?)

    fun checkObject(entity: Entity, isPlayer: Boolean): CheckObjectInfo? {
        return gp.arrObj.mapIndexed { index, obj ->
            if (obj == null) {
                null
            } else {
                entity.solidArea().x += entity.worldX
                entity.solidArea().y += entity.worldY

                obj.solidArea.x += obj.worldX
                obj.solidArea.y += obj.worldY

                fun evaluateCheckObjectInfo(obj: SuperObject) = if (entity.solidArea().intersects(obj.solidArea)) {
                    CheckObjectInfo(obj.collision, if (isPlayer) index else null)
                } else {
                    null
                }

                val result: CheckObjectInfo? = when (entity.direction) {
                    UP -> {
                        entity.solidArea().y -= entity.speed
                        evaluateCheckObjectInfo(obj)
                    }

                    DOWN -> {
                        entity.solidArea().y += entity.speed
                        evaluateCheckObjectInfo(obj)
                    }

                    LEFT -> {
                        entity.solidArea().x -= entity.speed
                        evaluateCheckObjectInfo(obj)
                    }

                    RIGHT -> {
                        entity.solidArea().x += entity.speed
                        evaluateCheckObjectInfo(obj)
                    }


                    NOTHING -> {
                        null
                    }
                }
                entity.solidAreaToDefaultParams()
                obj.solidAreaToDefaultParams()
                result
            }
        }
            .reduce { l, r -> l ?: r }
    }
}