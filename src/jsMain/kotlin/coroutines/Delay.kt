package coroutines

import kotlinx.browser.window
import kotlin.coroutines.resume

suspend fun delayTest(millis: Int) {
    val wc = WaitContinuation<Unit>()
    wc.runWaitResume { window.setTimeout({ it.resume(Unit) }, millis) }
}
