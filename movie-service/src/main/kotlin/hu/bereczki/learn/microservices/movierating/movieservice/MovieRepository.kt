package hu.bereczki.learn.microservices.movierating.movieservice

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
internal interface MovieRepository : MongoRepository<Movie, ObjectId>