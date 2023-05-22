import objects.ObjBoots
import objects.ObjBox
import objects.ObjDoor
import objects.ObjKey
import panels.GamePanel

class AssetSetter(gp : GamePanel) {
    val gp = gp

    fun setObject(){
        gp.arrObj[0] = ObjKey()
        gp.arrObj[0]?.worldX = gp.tileSize * 21
        gp.arrObj[0]?.worldY = gp.tileSize * 23

        gp.arrObj[1] = ObjKey()
        gp.arrObj[1]?.worldX = gp.tileSize * 13
        gp.arrObj[1]?.worldY = gp.tileSize * 4

        gp.arrObj[2] = ObjDoor()
        gp.arrObj[2]?.worldX = gp.tileSize * 9
        gp.arrObj[2]?.worldY = gp.tileSize * 16

        gp.arrObj[3] = ObjBox()
        gp.arrObj[3]?.worldX = gp.tileSize * 30
        gp.arrObj[3]?.worldY = gp.tileSize * 23

        gp.arrObj[4] = ObjDoor()
        gp.arrObj[4]?.worldX = gp.tileSize * 30
        gp.arrObj[4]?.worldY = gp.tileSize * 19


        gp.arrObj[5] = ObjBoots()
        gp.arrObj[5]?.worldX = gp.tileSize * 26
        gp.arrObj[5]?.worldY = gp.tileSize * 7
    }
}
