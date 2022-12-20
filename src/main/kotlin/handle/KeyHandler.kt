package handle

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyHandler : KeyListener {

    var upPressed: Boolean = false
    var downPressed: Boolean = false
    var leftPressed: Boolean = false
    var rightPressed: Boolean = false

    override fun keyTyped(e: KeyEvent?) {

    }

    override fun keyPressed(e: KeyEvent?) {
        extracted(e?.keyCode, true)
    }

    private fun extracted(code: Int?, setBool: Boolean) {
        when(code){
            KeyEvent.VK_UP -> upPressed = setBool
            KeyEvent.VK_DOWN -> downPressed = setBool
            KeyEvent.VK_LEFT -> leftPressed = setBool
            KeyEvent.VK_RIGHT -> rightPressed = setBool
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        extracted(e?.keyCode, false)
    }
}