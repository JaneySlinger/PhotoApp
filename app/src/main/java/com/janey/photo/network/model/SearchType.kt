package com.janey.photo.network.model

sealed class SearchType {
    data class Term(val searchTerm: String) : SearchType()
    data class User(val userId: String) : SearchType()
    data class Tag(val tags: String, val tagType: TagType) : SearchType()
}

enum class TagType {
    ANY,
    ALL,
}