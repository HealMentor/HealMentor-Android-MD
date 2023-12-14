package com.ch2ps215.mentorheal.presentation.kemungkinan.component

data class Rating(
    var items: Items?,
) {
    data class Items(
        var rate: Int?,
    ) {
        // Mengubah nilai rating menjadi jangkauan 1-100
        fun toAdjustedRating(): Int? {
            return rate?.let { it * 100 }
        }

        fun toPairList() = arrayListOf<Pair<String, Int?>>().apply {
            toAdjustedRating()?.let { adjustedRating ->
                add(Pair("1", adjustedRating))
            }
        }

        fun numberOfVotes() = toPairList().sumOf { it.second ?: 0 }.toLong()
    }
}

val items = Rating.Items(
    40, // Rating dalam jangkauan 0-1
)

val rating = Rating(
    items,
)