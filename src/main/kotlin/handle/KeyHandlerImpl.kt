package handle

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyHandlerImpl : KeyHandler {

    override var upPressed: Boolean = false
    override var downPressed: Boolean = false
    override var leftPressed: Boolean = false
    override var rightPressed: Boolean = false
    override var firePressed: Boolean = false

    override fun keyTyped(e: KeyEvent?) {

    }

    override fun keyPressed(e: KeyEvent?) {
        extracted(e?.keyCode, true)
    }

    private fun extracted(code: Int?, setBool: Boolean) {
        when (code) {
            KeyEvent.VK_UP -> upPressed = setBool
            KeyEvent.VK_DOWN -> downPressed = setBool
            KeyEvent.VK_LEFT -> leftPressed = setBool
            KeyEvent.VK_RIGHT -> rightPressed = setBool
            KeyEvent.VK_X -> firePressed = setBool
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        extracted(e?.keyCode, false)
    }
}

interface KeyHandler : KeyListener {
    var upPressed: Boolean
    var downPressed: Boolean
    var leftPressed: Boolean
    var rightPressed: Boolean
    var firePressed: Boolean
}