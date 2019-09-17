package pl.szymonmazanik.gotapp.utils.extensions

import pl.szymonmazanik.gotapp.model.entity.Character


fun Character.assignIdFromUrl() {
    id = """\d+$""".toRegex().find(url)?.value?.toLong()
        ?: throw Exception("Could not extract data source id")
}

