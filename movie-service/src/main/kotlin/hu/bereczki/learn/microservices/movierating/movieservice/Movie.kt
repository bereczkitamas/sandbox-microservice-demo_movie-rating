package hu.bereczki.learn.microservices.movierating.movieservice

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document(collection = "movies")
internal class Movie(
    @Id
    @Field(value = "_id")
    var id: ObjectId? = null,
    var plot: String,
    val genres: MutableList<String?>,
    var runtime: Int = 0,
    val cast: MutableList<String?>,
    var poster: String? = null,
    var title: String,
    var fullplot: String? = null,
    val countries: MutableList<String?>,
    var released: Instant? = null
)