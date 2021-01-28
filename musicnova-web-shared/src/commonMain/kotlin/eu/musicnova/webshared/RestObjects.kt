package eu.musicnova.webshared

data class RESTLoginRequestResponse(
    val status: LoginRequestResponseStatus,
    val message: String? = null
)