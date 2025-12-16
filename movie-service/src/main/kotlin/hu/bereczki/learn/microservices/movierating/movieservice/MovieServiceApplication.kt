package hu.bereczki.learn.microservices.movierating.movieservice

import co.elastic.otel.agent.attach.RuntimeAttach
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MovieServiceApplication

fun main(args: Array<String>) {
    RuntimeAttach.attachJavaagentToCurrentJvm()
    runApplication<MovieServiceApplication>(*args)
}
