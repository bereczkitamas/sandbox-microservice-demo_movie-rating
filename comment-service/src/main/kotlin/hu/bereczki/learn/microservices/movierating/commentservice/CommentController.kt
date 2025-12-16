package hu.bereczki.learn.microservices.movierating.commentservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class CommentController(val commentIndexSearch: CommentIndexSearch, val restTemplate: RestTemplate) {

    @GetMapping("/comments", params = ["keywords"])
    fun comments(@RequestParam keywords: String = "Tyrell"): List<Map<*, *>?> {
        return commentIndexSearch.commentsByKeywordsearch(keywords)
            .map {
                restTemplate.getForObject("http://localhost:8888/api/movies/${it["movie_id"]}", Map::class.java)
            }
    }

}