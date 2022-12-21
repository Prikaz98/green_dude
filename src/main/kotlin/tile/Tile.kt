package tile

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class Tile(collision: Boolean, image: BufferedImage) {
    val image = image
    val collision = collision
}

enum class TileEnum : TileEn {
    SAND {
        override fun getTile(): Tile {
            return Tile(false, ImageIO.read(javaClass.getResourceAsStream("/tiles/sand.jpg")))
        }
    },
    WALL {
        override fun getTile(): Tile {
            return Tile(true, ImageIO.read(javaClass.getResourceAsStream("/tiles/wall.png")))
        }
    },
    WATER {
        override fun getTile(): Tile {
            return Tile(true, ImageIO.read(javaClass.getResourceAsStream("/tiles/water.png")))
        }
    },
    EARTH {
        override fun getTile(): Tile {
            return Tile(false, ImageIO.read(javaClass.getResourceAsStream("/tiles/earth.png")))
        }
    },
    GRASS {
        override fun getTile(): Tile {
            return Tile(false, ImageIO.read(javaClass.getResourceAsStream("/tiles/grass.png")))
        }
    },
    TREE {
        override fun getTile(): Tile {
            return Tile(true, ImageIO.read(javaClass.getResourceAsStream("/tiles/tree.png")))
        }
    }
}

interface TileEn {
    fun getTile(): Tile
}