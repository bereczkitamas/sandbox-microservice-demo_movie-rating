package hu.bereczki.learn.microservices.movierating.commentservice

import co.elastic.otel.agent.attach.RuntimeAttach
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommentServiceApplication

fun main(args: Array<String>) {
    RuntimeAttach.attachJavaagentToCurrentJvm()
    runApplication<CommentServiceApplication>(*args)
}
