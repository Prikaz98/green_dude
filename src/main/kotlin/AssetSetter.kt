import objects.ObjBox
import objects.ObjDoor
import objects.ObjKey
import panels.GamePanel

class AssetSetter(gp : GamePanel) {
    val gp = gp

    fun setObject(){
        gp.arrObj[0] = ObjKey()
        gp.arrObj[0]?.worldX = gp.tileSize * 5
        gp.arrObj[0]?.worldY = gp.tileSize * 2

        gp.arrObj[1] = ObjKey()
        gp.arrObj[1]?.worldX = gp.tileSize * 22
        gp.arrObj[1]?.worldY = gp.tileSize * 2

        gp.arrObj[2] = ObjDoor()
        gp.arrObj[2]?.worldX = gp.tileSize * 15
        gp.arrObj[2]?.worldY = gp.tileSize * 20

        gp.arrObj[3] = ObjBox()
        gp.arrObj[3]?.worldX = gp.tileSize * 26
        gp.arrObj[3]?.worldY = gp.tileSize * 27
    }
}