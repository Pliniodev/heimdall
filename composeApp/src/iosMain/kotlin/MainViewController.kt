import androidx.compose.ui.window.ComposeUIViewController
import com.pliniodev.heimdall.App
import com.pliniodev.heimdall.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin().koin
    App()
}