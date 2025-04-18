package io.appwrite

import io.appwrite.extensions.toJson
import io.appwrite.extensions.fromJson

/**
 * Helper class to generate query strings.
 */
class Query(
    val method: String,
    val attribute: String? = null,
    val values: List<Any>? = null,
) {
    /**
     * Convert the query object to a JSON string.
     *
     * @returns The JSON string representation of the query object.
     */
    override fun toString() = this.toJson()

    companion object {

        /**
         * Filter resources where attribute is equal to value.
         *
         * @param attribute The attribute to filter on.
         * @param value The value to compare against.
         * @returns The query string.
         */
        fun equal(attribute: String, value: Any) = Query("equal", attribute, parseValue(value)).toJson()

        /**
         * Filter resources where attribute is not equal to value.
         *
         * @param attribute The attribute to filter on.
         * @param value The value to compare against.
         * @returns The query string.
         */
        fun notEqual(attribute: String, value: Any) = Query("notEqual", attribute, parseValue(value)).toJson()

        /**
         * Filter resources where attribute is less than value.
         *
         * @param attribute The attribute to filter on.
         * @param value The value to compare against.
         * @returns The query string.
         */
        fun lessThan(attribute: String, value: Any) = Query("lessThan", attribute, parseValue(value)).toJson()

        /**
         * Filter resources where attribute is less than or equal to value.
         *
         * @param attribute The attribute to filter on.
         * @param value The value to compare against.
         * @returns The query string.
         */
        fun lessThanEqual(attribute: String, value: Any) = Query("lessThanEqual", attribute, parseValue(value)).toJson()

        /**
         * Filter resources where attribute is greater than value.
         *
         * @param attribute The attribute to filter on.
         * @param value The value to compare against.
         * @returns The query string.
         */
        fun greaterThan(attribute: String, value: Any) = Query("greaterThan", attribute, parseValue(value)).toJson()

        /**
         * Filter resources where attribute is greater than or equal to value.
         *
         * @param attribute The attribute to filter on.
         * @param value The value to compare against.
         * @returns The query string.
         */
        fun greaterThanEqual(attribute: String, value: Any) = Query("greaterThanEqual", attribute, parseValue(value)).toJson()

        /**
         * Filter resources where attribute matches the search value.
         *
         * @param attribute The attribute to filter on.
         * @param value The search value to match against.
         * @returns The query string.
         */
        fun search(attribute: String, value: String) = Query("search", attribute, listOf(value)).toJson()

        /**
         * Filter resources where attribute is null.
         *
         * @param attribute The attribute to filter on.
         * @returns The query string.
         */
        fun isNull(attribute: String) = Query("isNull", attribute).toJson()

        /**
         * Filter resources where attribute is not null.
         *
         * @param attribute The attribute to filter on.
         * @returns The query string.
         */
        fun isNotNull(attribute: String) = Query("isNotNull", attribute).toJson()

        /**
         * Filter resources where attribute is between start and end (inclusive).
         *
         * @param attribute The attribute to filter on.
         * @param start The start value of the range.
         * @param end The end value of the range.
         * @returns The query string.
         */
        fun between(attribute: String, start: Any, end: Any) = Query("between", attribute, listOf(start, end)).toJson()

        /**
         * Filter resources where attribute starts with value.
         *
         * @param attribute The attribute to filter on.
         * @param value The value to compare against.
         * @returns The query string.
         */
        fun startsWith(attribute: String, value: String) = Query("startsWith", attribute, listOf(value)).toJson()

        /**
         * Filter resources where attribute ends with value.
         *
         * @param attribute The attribute to filter on.
         * @param value The value to compare against.
         * @returns The query string.
         */
        fun endsWith(attribute: String, value: String) = Query("endsWith", attribute, listOf(value)).toJson()

        /**
         * Specify which attributes should be returned by the API call.
         *
         * @param attributes The list of attributes to select.
         * @returns The query string.
         */
        fun select(attributes: List<String>) = Query("select", null, attributes).toJson()

        /**
         * Sort results by attribute ascending.
         *
         * @param attribute The attribute to sort by.
         * @returns The query string.
         */
        fun orderAsc(attribute: String) = Query("orderAsc", attribute).toJson()

        /**
         * Sort results by attribute descending.
         *
         * @param attribute The attribute to sort by.
         * @returns The query string.
         */
        fun orderDesc(attribute: String) = Query("orderDesc", attribute).toJson()

        /**
         * Return results before documentId.
         *
         * @param documentId The document ID to use as cursor.
         * @returns The query string.
         */
        fun cursorBefore(documentId: String) = Query("cursorBefore", null, listOf(documentId)).toJson()

        /**
         * Return results after documentId.
         *
         * @param documentId The document ID to use as cursor.
         * @returns The query string.
         */
        fun cursorAfter(documentId: String) = Query("cursorAfter", null, listOf(documentId)).toJson()
        
        /**
         * Return only limit results.
         *
         * @param limit The number of results to return.
         * @returns The query string.
         */
        fun limit(limit: Int) = Query("limit", null, listOf(limit)).toJson()

        /**
         * Filter resources by skipping the first offset results.
         *
         * @param offset The number of results to skip.
         * @returns The query string.
         */
        fun offset(offset: Int) = Query("offset", null, listOf(offset)).toJson()

        /**
         * Filter resources where attribute contains the specified value.
         *
         * @param attribute The attribute to filter on.
         * @param value The value to compare against.
         * @returns The query string.
         */
        fun contains(attribute: String, value: Any) = Query("contains", attribute, parseValue(value)).toJson()

        /**
         * Combine multiple queries using logical OR operator.
         *
         * @param queries The list of query strings to combine.
         * @returns The query string.
         */
        fun or(queries: List<String>) = Query("or", null, queries.map { it.fromJson<Query>() }).toJson()

        /**
         * Combine multiple queries using logical AND operator.
         *
         * @param queries The list of query strings to combine.
         * @returns The query string.
         */
        fun and(queries: List<String>) = Query("and", null, queries.map { it.fromJson<Query>() }).toJson()

        /**
         * Parse the value to a list of values.
         *
         * @param value The value to parse.
         * @returns The list of parsed values.
         */
        private fun parseValue(value: Any): List<Any> {
            return when (value) {
                is List<*> -> value as List<Any>
                else -> listOf(value)
            }
        }
    }
}