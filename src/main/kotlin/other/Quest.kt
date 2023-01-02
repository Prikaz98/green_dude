package other

/**
 * If quest ready once then we think quest is already done
 */
data class Quest (val description : String, val checkReady: () -> Boolean){
    var ready = false
    fun info(): String {
        if(!ready){
            ready = checkReady.invoke()
            val readyDesc = if(ready)  "✓" else ""
            return "$description $readyDesc"
        } else {
            return "$description ✓"
        }
    }
}
