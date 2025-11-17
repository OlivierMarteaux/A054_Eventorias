package com.oliviermarteaux.a054_eventorias.ui.screen.home

import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post

enum class SortOption(val comparator: Comparator<Post>) {
    TITLE(compareBy { it.title }),
    DATE_ASCENDING(compareBy { it.date }),
    DATE_DESCENDING(compareByDescending { it.date })
}