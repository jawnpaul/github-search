package com.example.githubsearch.features.result.presentation.mapper

import com.example.githubsearch.UnitTest
import com.example.githubsearch.features.result.domain.model.SearchResult
import com.example.githubsearch.features.result.presentation.model.SearchResultPresentation
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Test

class DomainToPresentationTest : UnitTest() {

    @Test
    fun `domain to presentation should return presentation object`() {
        val searchResult = SearchResult("syz", "john", "User")

        assertThat(searchResult.toPresentation()).isEqualTo(SearchResultPresentation("syz",
            "john",
            "User"))
    }
}