package com.fatec.estoque_api_kmp.routes

import com.fatec.estoque_api_kmp.domain.models.StockItem
import com.fatec.estoque_api_kmp.domain.models.StockItemInsert
import com.fatec.estoque_api_kmp.domain.models.StockItemUpdate
import com.fatec.estoque_api_kmp.domain.models.StockSummary
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Route.stockRoutes(supabase: SupabaseClient) {

    route("/stock") {
        get {
            try {
                val stock = supabase
                    .postgrest["stock_items"]
                    .select()
                    .decodeList<StockItem>()

                call.respond(stock)

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

                val item = supabase
                    .postgrest["stock_items"]
                    .select {
                        filter {
                            eq("id", id!!)
                        }
                    }
                    .decodeSingle<StockItem>()

                call.respond(item)

            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.NotFound,
                    mapOf("error" to "Item de estoque não encontrado")
                )
            }
        }

        post {
            try {
                val item = call.receive<StockItemInsert>()

                val result = supabase
                    .postgrest["stock_items"]
                    .insert(item) {
                        select()
                    }
                    .decodeSingle<StockItem>()

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
                val item = call.receive<StockItemUpdate>()

                supabase
                    .postgrest["stock_items"]
                    .update(item) {
                        filter {
                            eq("id", id!!)
                        }
                    }

                call.respond(
                    HttpStatusCode.OK,
                    mapOf("message" to "Item de estoque atualizado")
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
                    .postgrest["stock_items"]
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
        get("/summary") {
            try {

                val result = supabase
                    .postgrest
                    .rpc("get_stock_summary")
                    .decodeList<StockSummary>()

                call.respond(result)

            } catch (e: Exception) {
                call.respond(mapOf("error" to e.message))
            }
        }
    }
}