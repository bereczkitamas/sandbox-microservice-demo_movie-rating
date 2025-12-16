package hu.bereczki.learn.microservices.movierating.commentservice

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.search.SearchOperator
import com.mongodb.client.model.search.SearchOptions
import com.mongodb.client.model.search.SearchPath
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class CommentIndexSearch(val mongoTemplate: MongoTemplate) {

    fun commentsByKeywordsearch(keywords: String): Collection<Document> {

        val collection: MongoCollection<Document> = mongoTemplate.getDb().getCollection("comments")
        val pipeline = listOf(
            Aggregates.search(
                SearchOperator.text(
                    SearchPath.wildcardPath("*"), keywords
                ),
                SearchOptions.searchOptions().index("comment-search")
            ),
            Aggregates.limit(3)
        )

        return collection.aggregate(pipeline).into<ArrayList<Document>>(ArrayList())
    }
}