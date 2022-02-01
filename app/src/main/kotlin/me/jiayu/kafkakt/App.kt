package me.jiayu.kafkakt

import me.jiayu.kafkakt.App.Companion.logger
import org.slf4j.LoggerFactory

class App {

    companion object {
        val logger = LoggerFactory.getLogger(App::class.java.name)!!
    }

    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    logger.info("test")
    println(App().greeting)
}
