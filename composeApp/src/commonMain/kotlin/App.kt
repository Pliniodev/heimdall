import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.pliniodev.heimdall.permissions.collectPermissionAsState
import com.pliniodev.heimdall.permissions.model.Permission
import com.pliniodev.heimdall.permissions.service.PermissionService
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        HomeContent()
    }
}

@Composable
fun HomeContent() {
    val permissionService = koinInject<PermissionService>()
    val permissionState by permissionService.collectPermissionAsState(
        Permission.STORAGE_READ_AND_WRITE,
    )
    val permission = Permission.STORAGE_READ_AND_WRITE
    val scope = rememberCoroutineScope()
    PermissionItem(
        permissionName = permission.title,
        permissionState = permissionState,
        onRequestClick = {
            scope.launch {
                permissionService.providePermission(permission)
            }
        },
        onOpenSettingsClick = {
            permissionService.openSettingPage(permission)
        },
    )
}
