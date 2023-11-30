package com.example

import com.example.models.Customer
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun testPostCustomer() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        client.post("/customer") {
            contentType(ContentType.Application.Json)
            setBody(Customer("1", "den", "gin", "atv@mil.ut"))
        }.apply {
            assertEquals("Customer stored correctly", bodyAsText())
            assertEquals(HttpStatusCode.Created, status)
        }
    }
}
