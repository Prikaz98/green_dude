package handle

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KeyHandler : KeyListener {

    var upPressed: Boolean? = null
    var downPressed: Boolean? = null
    var leftPressed: Boolean? = null
    var rightPressed: Boolean? = null

    override fun keyTyped(e: KeyEvent?) {

    }

    override fun keyPressed(e: KeyEvent?) {
        val code: Int? = e?.keyCode
        extracted(code, true)
    }

    private fun extracted(code: Int?, setBool: Boolean) {
        if (code == KeyEvent.VK_W) {
            upPressed = setBool
        }
        if (code == KeyEvent.VK_S) {
            downPressed = setBool
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = setBool
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = setBool
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        val code: Int? = e?.keyCode
        extracted(code, false)
    }
}