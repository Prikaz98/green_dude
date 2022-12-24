package objects

import utils.ImageLoader

class ObjKey : SuperObject() {
    init {
        name = "Key"
        image = ImageLoader.getImage("/objects/key.png")
    }
}