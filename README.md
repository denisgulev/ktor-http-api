# ktor-http-api

## NEXT STEPS
#### Authentication: 
currently, the API is open to whomever would like to access it. If you want to restrict access, have a look at Ktor's support for JWT and other authentication methods.

#### Learn more about route organization! 
If you'd like to learn about different ways to organize your routes with Ktor, check out the Routing topic.

#### Persistence!
Currently, all customers and orders vanish when we stop our application, as we are only storing them in an in-memory storage. You could try integrating your application with a database like PostgreSQL or MongoDB, using one of the plenty projects that allow database access from Kotlin, like Exposed or KMongo.

#### Integrate with a client!
Now that we are exposing data, it would make sense to explore how this data can be consumed again! Try writing an API client using the Ktor HTTP client, for example, or try accessing it from a website using JavaScript or Kotlin/JS!

To make sure your API works nicely with browser clients, you should also set up a policy for Cross-Origin Resource Sharing (CORS). The simplest and most permissive way to do this with Ktor would be by adding the following snippet to the top of Application.module():

install(CORS) {
    anyHost()
}
