package xyz.teamgravity.navigation3basics

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform