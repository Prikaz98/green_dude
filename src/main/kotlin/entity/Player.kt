package entity

import handle.KeyHandler
import panels.GamePanel
import java.awt.Color
import java.awt.Graphics2D

class Player(
    val gp : GamePanel,
    val keyH : KeyHandler
) : Entity(4) {
    init {
        x = 100
        y = 100
    }
    fun update() {
        when {
            keyH.upPressed == true -> y = y?.minus(speed)
            keyH.downPressed == true -> y = y?.plus(speed)
            keyH.leftPressed == true -> x = x?.minus(speed)
            keyH.rightPressed == true -> x = x?.plus(speed)
        }
    }
    fun draw(g2 : Graphics2D) {
        g2.color = Color.white
        g2.fillRect(x!!, y!!, gp.tileSize, gp.tileSize)
    }
}