import handle.KeyHandler
import java.awt.Graphics2D

interface DrawElement {
    fun draw(g2: Graphics2D)
    fun update(keyHandler: KeyHandler)
    fun init(): DrawElement
}
