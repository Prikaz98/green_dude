package objects

import utils.ImageLoader

class ObjBox : SuperObject() {
    init {
        name = "Key"
        image = ImageLoader.getImage("/objects/box.png")
    }
}