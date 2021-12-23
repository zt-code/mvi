
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSONObject

const val BASE_URL = "https://chengshidianliang.net"

fun inflate(
    context: Context,
    viewId: Int,
    parent: ViewGroup? = null,
    attachToRoot: Boolean = false
): View {
    return LayoutInflater.from(context).inflate(viewId, parent, attachToRoot)
}

//LCE -> Loading/Content/Error
/*sealed class PageState<out T> {
    data class Success<T>(val data: T) : PageState<T>()
    data class Error<T>(var message: String) : PageState<T>() {
        constructor(t: Throwable) : this(t.message ?: "")
    }
}*/

/*sealed class PageState<out T> {
    data class Success<T>(val data: T) : PageState<T>()
    data class Error<T>(var message: String) : PageState<T>() {
        constructor(t: Throwable) : this(t.message ?: "")
    }
}*/

/*sealed class NetState<T> {
    data class SuccessMap<T>(val data: JSONObject) : NetState<T>()
    data class SuccessList<T>(val data: List<JSONObject>) : NetState<T>()
    data class SuccessStr<T>(val data: String) : NetState<T>()
    data class Error<T>(val msg: JSONObject) : NetState<T>()
}*/

sealed class NetState<T> {
    data class Success<T>(val data: T) : NetState<T>()
    data class Error<T>(val msg: JSONObject) : NetState<T>()
}

/*sealed class NetState<T> {
    data class Success<T>(val data: T) : NetState<T>()
    data class Succes1s<T>(val data: List<T>) : NetState<T>()
    data class Error<T>(val msg: JSONObject) : NetState<T>()
}*/

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}

sealed class RefreshStatus {
    object RefreshIng : RefreshStatus()
    object RefreshEnd : RefreshStatus()
    object LoadMoreIng : RefreshStatus()
    object LoadMoreEnd : RefreshStatus()
    object NotRefresh : RefreshStatus()
}

