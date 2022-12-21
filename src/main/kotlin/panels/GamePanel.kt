package panels

import CollisionChecker
import entity.Player
import handle.KeyHandlerImpl
import tile.TileManager
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JPanel

class GamePanel : JPanel, Runnable {

    val originalTileSize: Int = 16 //16x16
    val scale: Int = 4
    val tileSize: Int = originalTileSize * scale // 48x48 tile
    val maxScreenCol = 16
    val maxScreenRow = 12
    val screenWidth = tileSize * maxScreenCol //768 px
    val screenHeight = tileSize * maxScreenRow //576 px
    var gameThread: Thread? = null

    val FPS = 60.0
    val collisionChecker = CollisionChecker(this)
    val player: Player = Player(this).init()
    val tileManager = TileManager(this)
    val keyH = KeyHandlerImpl()

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
        gameLoopStart()
    }

    private fun gameLoopStart(){
        val drawInterval = 1_000_000_000 / FPS // 0.016666.. seconds
        var delta = 0.0
        var lastTime : Long = System.nanoTime()
        var currentTime : Long?

        while (gameThread != null) {
            currentTime = System.nanoTime()

            delta += (currentTime - lastTime) / drawInterval
            lastTime = currentTime

            if(delta >= 1) {
                //1. UPDATE: information such as character position
                update()
                //2. DRAW: draw the screen with the updated information
                repaint()
                delta--
            }
        }
    }

    fun update() {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            player.update(keyH)
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g2: Graphics2D = g as Graphics2D

        tileManager.draw(g2)

        player.draw(g2)

        g2.dispose()
    }
}