package hu.bereczki.learn.microservices.movierating.movieservice

import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull

@RestController
internal class MovieController(private val movieRepository: MovieRepository) {
    @GetMapping("/")
    fun movies(pageable: Pageable): Page<Movie> {
        return movieRepository.findAll(pageable)
    }

    @GetMapping("/{id}")
    fun movie(@PathVariable id: String): Movie? {
        return movieRepository.findById(ObjectId(id)).getOrNull()
    }
}