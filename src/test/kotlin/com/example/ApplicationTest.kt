package com.example

import com.example.models.Customer
import com.example.models.customerStorage
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.internal.readJson
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testGetCustomers() = testApplication {
        client.get("/customer").apply {
            assertEquals(0, customerStorage.size)
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testGetCustomer_withNotExistingID() = testApplication {
        client.get("/customer/2").apply {
            assertEquals("Not found id: 2", bodyAsText())
            assertEquals(HttpStatusCode.NotFound, status)
        }
    }

    @Test
    fun testPostCustomer() = testApplication {
        val testCustomer = Customer("1", "den", "gin", "atv@mil.ut")
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        // save a customer
        client.post("/customer") {
            contentType(ContentType.Application.Json)
            setBody(testCustomer)
        }.apply {
            assertEquals("Customer stored correctly", bodyAsText())
            assertEquals(HttpStatusCode.Created, status)
        }

        // retrieve the saved customer
        client.get("/customer/1").apply {
            val retrievedCustomer = body() as Customer
            assertEquals(testCustomer, retrievedCustomer)
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    @Test
    fun testDeleteCustomer() = testApplication {
        val testCustomer = Customer("2", "dan", "nig", "atv@mil.ut")
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        // save a customer
        client.post("/customer") {
            contentType(ContentType.Application.Json)
            setBody(testCustomer)
        }.apply {
            assertEquals("Customer stored correctly", bodyAsText())
            assertEquals(HttpStatusCode.Created, status)
        }

        // retrieve the saved customer
        client.get("/customer/2").apply {
            val retrievedCustomer = body() as Customer
            assertEquals(testCustomer, retrievedCustomer)
            assertEquals(HttpStatusCode.OK, status)
        }

        // delete saved customer and test it is not present
        client.delete("/customer/2").apply {
            assertEquals(HttpStatusCode.Accepted, status)
            assertEquals("Customer removed correctly", bodyAsText())
        }
    }
}
