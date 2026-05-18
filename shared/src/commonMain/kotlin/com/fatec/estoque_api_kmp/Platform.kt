package com.fatec.estoque_api_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform