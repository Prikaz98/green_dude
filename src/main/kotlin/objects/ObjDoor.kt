package objects

import utils.ImageLoader

class ObjDoor : SuperObject() {
    init {
        name = "Door"
        image = ImageLoader.getImage("/objects/door.png")
        collision = true
    }
}