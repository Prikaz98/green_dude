import panels.GamePanel
import javax.swing.JFrame

fun main(args: Array<String>) {
    println(args)
    val window = JFrame()
    window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    window.isResizable = false
    window.title = "2d Adventure"

    val gamePanel = GamePanel();
    window.add(gamePanel)

    window.pack()

    window.setLocationRelativeTo(null)
    window.isVisible = true
    gamePanel.setupGame()
    gamePanel.startGameThread()
}