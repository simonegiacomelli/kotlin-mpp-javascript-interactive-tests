package interactive

import coroutines.WaitContinuation
import coroutines.delayTest
import kotlinx.browser.document
import kotlinx.coroutines.test.runTest
import org.w3c.dom.HTMLButtonElement
import kotlin.test.Test

class ShowElementsTest {
    private val body get() = document.body!!

    @Test
    fun test() = runTest {

        listOf(
            "a new line of text will appear after 1 second",
            "this is a small demo to showcase how to interactively troubleshoot kotlin/js tests",
            "Using a different version of delay() you can put the code on hold to inspect visually",
            "The test will terminate after you click the button (twice)"
        ).forEach { line -> addAndWait(line) }

        val btn = htmlButtonElement()
        btn.innerHTML = "click me"
        val wait = WaitContinuation<Unit>()
        var clickCount = 0
        btn.onclick = {
            clickCount++
            btn.innerHTML = "$clickCount click${if (clickCount != 1) "s" else ""} done!"
            if (clickCount == 2) wait.resume(Unit)
        }
        wait.runWaitResume { body.append(btn) }
        addAndWait("The test will exit in 1 second. Bye bye! ")
    }

    private fun htmlButtonElement() = document.createElement("button") as HTMLButtonElement

    private suspend fun addAndWait(it: String) {
        val h3 = document.createElement("h3")
        h3.innerHTML = "<h3>$it</h3>"
        body.append(h3)
        delayTest(1000)
    }

}