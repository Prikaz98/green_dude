package panels

import AssetSetter
import CollisionChecker
import entity.Player
import handle.KeyHandlerImpl
import objects.SuperObject
import tile.TileManager
import java.awt.*
import javax.swing.JPanel

class GamePanel : JPanel, Runnable {

    val gpFont = Font(Font.MONOSPACED,Font.BOLD,17)

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
    val assetSetter = AssetSetter(this)
    val player: Player = Player(this)
    val arrObj: Array<SuperObject?> = arrayOfNulls(10)
    val tileManager = TileManager(this)
    val keyH = KeyHandlerImpl()

    constructor() {
        this.preferredSize = Dimension(screenWidth, screenHeight)
        this.background = Color.black
        this.isDoubleBuffered = true
        this.addKeyListener(keyH)
        this.isFocusable = true
    }

    fun setupGame() {
        assetSetter.setObject()
    }

    fun startGameThread() {
        gameThread = Thread(this)
        gameThread!!.start()
    }

    override fun run() {
        gameLoopStart()
    }

    private fun gameLoopStart() {
        val drawInterval = 1_000_000_000 / FPS // 0.016666.. seconds
        var delta = 0.0
        var lastTime: Long = System.nanoTime()
        var currentTime: Long?

        while (gameThread != null) {
            currentTime = System.nanoTime()

            delta += (currentTime - lastTime) / drawInterval
            lastTime = currentTime

            if (delta >= 1) {
                //1. UPDATE: information such as character position
                update()
                //2. DRAW: draw the screen with the updated information
                repaint()
                delta--
            }
        }
    }

    fun update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            player.update(keyH)
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val g2: Graphics2D = g as Graphics2D

        tileManager.draw(g2)
        g2.font = gpFont

        arrObj.forEach { it?.draw(g2,this) }

        player.draw(g2)

        g2.dispose()
    }
}