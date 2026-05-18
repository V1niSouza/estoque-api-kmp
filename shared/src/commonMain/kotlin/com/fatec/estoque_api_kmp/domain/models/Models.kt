package com.fatec.estoque_api_kmp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val description: String? = null,
    val sku: String,
    val category: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

@Serializable
data class ProductInsert(
    val name: String,
    val description: String? = null,
    val sku: String,
    val category: String? = null
)

@Serializable
data class ProductUpdate(
    val name: String?,
    val description: String? = null,
    val sku: String?,
    val category: String? = null
)

@Serializable
data class StockItem(
    val id: String,
    @SerialName("product_id")
    val productId: String,
    val quantity: Int,
    @SerialName("unit_price")
    val unitPrice: Double,
    val location: String? = null,
    @SerialName("updated_at")
    val updatedAt: String? = null
)

@Serializable
data class StockItemInsert(
    @SerialName("product_id")
    val productId: String,
    val quantity: Int,
    @SerialName("unit_price")
    val unitPrice: Double,
    val location: String? = null
)

@Serializable
data class StockItemUpdate(
    val quantity: Int? = null,
    @SerialName("unit_price")
    val unitPrice: Double? = null,
    val location: String? = null
)

@Serializable
data class StockSummary(
    @SerialName("product_id")
    val productId: String,
    @SerialName("product_name")
    val productName: String,
    @SerialName("total_quantity")
    val totalQuantity: Int
)