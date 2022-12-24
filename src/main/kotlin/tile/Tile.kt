package tile

import utils.ImageLoader
import java.awt.image.BufferedImage

class Tile(collision: Boolean, image: BufferedImage) {
    val image = image
    val collision = collision
}

enum class TileEnum : TileEn {
    SAND { /*0*/
        override fun getTile(): Tile {
            return Tile(false, ImageLoader.getImage("/tiles/sand.jpg"))
        }
    },
    WALL { /*1*/
        override fun getTile(): Tile {
            return Tile(true, ImageLoader.getImage("/tiles/wall.png"))
        }
    },
    WATER { /*2*/
        override fun getTile(): Tile {
            return Tile(true, ImageLoader.getImage("/tiles/water.png"))
        }
    },
    EARTH { /*3*/
        override fun getTile(): Tile {
            return Tile(false, ImageLoader.getImage("/tiles/earth.png"))
        }
    },
    GRASS { /*4*/
        override fun getTile(): Tile {
            return Tile(false, ImageLoader.getImage("/tiles/grass.png"))
        }
    },
    TREE { /*5*/
        override fun getTile(): Tile {
            return Tile(true, ImageLoader.getImage("/tiles/tree.png"))
        }
    }
}

interface TileEn {
    fun getTile(): Tile
}