package com.selfformat.yourrandomquote

import com.google.firebase.database.DatabaseReference
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Test

class QuoteDetailsViewModelTest {
    private val mockDatabaseReference = mock<DatabaseReference>()
    private val viewModel = QuoteDetailsViewModel(mockDatabaseReference)

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
