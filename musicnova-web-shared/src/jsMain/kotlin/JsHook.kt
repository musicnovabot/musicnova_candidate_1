import kotlinx.browser.window

fun main() {
    window.asDynamic().kotlinPart = JsEntryPoint
}