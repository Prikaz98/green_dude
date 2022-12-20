package panels

import entity.Player
import handle.KeyHandler
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JPanel

class GamePanel : JPanel, Runnable {

    val originalTileSize: Int = 16 //16x16
    val scale: Int = 3
    val tileSize: Int = originalTileSize * scale// 48x48 tile
    val maxScreenCol = 16
    val maxScreenRow = 12
    val screenWidth = tileSize * maxScreenCol //768 px
    val screenHeight = tileSize * maxScreenRow //576 px
    var gameThread: Thread? = null

    val FPS = 60.0
    val keyH = KeyHandler()

    val player = Player(this,keyH)

    constructor() {
        this.preferredSize = Dimension(screenWidth, screenHeight)
        this.background = Color.black
        this.isDoubleBuffered = true
        this.addKeyListener(keyH)
        this.isFocusable = true
    }

    fun startGameThread() {
        gameThread = Thread(this)
        gameThread!!.start()
    }

    override fun run() {
        loopWithoutThreadSleep()
    }

    private fun loopWithoutThreadSleep(){
        val drawInterval = 1_000_000_000 / FPS // 0.016666.. seconds
        var delta = 0.0
        var lastTime : Long = System.nanoTime()
        var currentTime : Long?
        var timer = 0L
        var drawCount = 0

        while (gameThread != null) {
            currentTime = System.nanoTime()

            delta += (currentTime - lastTime) / drawInterval
            timer += (currentTime - lastTime)
            lastTime = currentTime

            if(delta >= 1) {
                //1. UPDATE: information such as character position
                update()
                //2. DRAW: draw the screen with the updated information
                repaint()
                delta--
                drawCount++
            }
            if(timer >= 1_000_000_000){
                println("FPS : $drawCount")
                drawCount = 0
                timer = 0
            }
        }
    }

    fun update() {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            player.update()
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g2: Graphics2D = g as Graphics2D
        player.draw(g2)

        g2.dispose()
    }
}