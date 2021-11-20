package bot.tx.wsure.top.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*

fun Application.configureHTTP() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
    }

}
