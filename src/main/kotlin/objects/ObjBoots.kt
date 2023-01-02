package objects

import utils.ImageLoader

class ObjBoots : SuperObject() {
    init {
        name = "Boots"
        image = ImageLoader.getImage("/objects/socket.png")
        collision = false
    }
}