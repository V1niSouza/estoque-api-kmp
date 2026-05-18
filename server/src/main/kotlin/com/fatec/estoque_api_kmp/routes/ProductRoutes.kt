package com.fatec.estoque_api_kmp.routes

import com.fatec.estoque_api_kmp.domain.models.Product
import com.fatec.estoque_api_kmp.domain.models.ProductInsert
import com.fatec.estoque_api_kmp.domain.models.ProductUpdate
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.productRoutes(supabase: SupabaseClient) {

    route("/products") {

        get {
            try {
                val products = supabase
                    .postgrest["products"]
                    .select()
                    .decodeList<Product>()

                call.respond(products)

            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("error" to e.message)
                )
            }
        }

        get("/{id}") {
            try {

                val id = call.parameters["id"]

                val product = supabase
                    .postgrest["products"]
                    .select {
                        filter {
                            eq("id", id!!)
                        }
                    }
                    .decodeSingle<Product>()

                call.respond(product)

            } catch (e: Exception) {

                call.respond(
                    HttpStatusCode.NotFound,
                    mapOf("error" to "Produto não encontrado")
                )
            }
        }

        post {

            try {

                val product = call.receive<ProductInsert>()

                val result = supabase
                    .postgrest["products"]
                    .insert(product) {
                        select()
                    }
                    .decodeSingle<Product>()

                call.respond(HttpStatusCode.Created, result)

            } catch (e: Exception) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            }
        }

        put("/{id}") {

            try {

                val id = call.parameters["id"]

                val product = call.receive<ProductUpdate>()

                supabase
                    .postgrest["products"]
                    .update(product) {
                        filter {
                            eq("id", id!!)
                        }
                    }

                call.respond(
                    HttpStatusCode.OK,
                    mapOf("message" to "Produto atualizado")
                )

            } catch (e: Exception) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            }
        }

        delete("/{id}") {

            try {

                val id = call.parameters["id"]

                supabase
                    .postgrest["products"]
                    .delete {
                        filter {
                            eq("id", id!!)
                        }
                    }

                call.respond(HttpStatusCode.NoContent)

            } catch (e: Exception) {

                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            }
        }
    }
}