package me.jiayu.kafkakt

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.jiayu.kafkakt.App.Companion.logger
import me.jiayu.kafkakt.App.Companion.prop
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class App {

    companion object {
        val logger = LoggerFactory.getLogger(App::class.java.name)!!

        val prop = {
            val prop = Properties()
            prop["application.id"] = "kafkakt"
            prop["group.id"] = "kafkakt-0001"
            prop["bootstrap.servers"] = "localhost:9092"
            prop["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
            prop["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
            prop["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
            prop["value.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
            prop
        }
    }
}


suspend fun main() = coroutineScope {
    val topic = "text-topic"
    logger.info("starting to listen to $topic")

    val handle = launch {
        val consumer = KafkaConsumer<String, String>(prop())
        consumer.use { c ->
            c.subscribe(Collections.singleton("text-topic"))
            while (true) {
                val records = consumer.poll(1.0.seconds.toJavaDuration())
                records.forEach { logger.info("key=${it.key()}, value=${it.value()}") }
            }
        }
    }

    val producer = KafkaProducer<String, String>(prop())
    producer.use { p ->
        (0..100).forEach { p.send(ProducerRecord("text-topic", "$it", "value $it")) }
    }

    delay(4.0.seconds)
    logger.info("cancelling")
    handle.cancelAndJoin()
}

