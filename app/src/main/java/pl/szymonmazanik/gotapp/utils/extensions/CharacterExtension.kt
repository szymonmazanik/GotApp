package pl.szymonmazanik.gotapp.utils.extensions

import pl.szymonmazanik.gotapp.model.entity.Character

/**
 * Assign id to current [Character] instance using [Regex]
 * API does not provide id. We need to extract it from URL
 */
fun Character.assignIdFromUrl() {
    id = """\d+$""".toRegex().find(url)?.value?.toLong()
        ?: throw Exception("Could not extract data source id")
}

