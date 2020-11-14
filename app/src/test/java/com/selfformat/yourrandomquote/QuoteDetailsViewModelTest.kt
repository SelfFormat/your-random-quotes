package com.selfformat.yourrandomquote

import com.google.firebase.database.DatabaseReference
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.selfformat.yourrandomquote.domain.DatabaseQuote
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class QuoteDetailsViewModelTest {
    private val mockDatabaseReference = mock<DatabaseReference>()
    private val viewModel = QuoteDetailsViewModel(mockDatabaseReference)
    private val mockedDatabaseQuote = mock<DatabaseQuote>()

    private val uid = "userID"
    private val quoteID = "quoteID"

    @Test
    fun `verify addQuote will be called with proper values`() {
        given(mockDatabaseReference.child(anyString())).willReturn(mockDatabaseReference)
        given(mockDatabaseReference.push()).willReturn(mockDatabaseReference)
        viewModel.addQuote(uid, mockedDatabaseQuote)
        verify(mockDatabaseReference).child("users")
        verify(mockDatabaseReference).child(uid)
        verify(mockDatabaseReference).child("quotes")
        verify(mockDatabaseReference).push()
        verify(mockDatabaseReference).setValue(mockedDatabaseQuote)
    }

    @Test
    fun `verify updateQuote will be called with proper values`() {
        given(mockDatabaseReference.child(anyString())).willReturn(mockDatabaseReference)
        viewModel.updateQuote(uid, quoteID, mockedDatabaseQuote)
        verify(mockDatabaseReference).child("users")
        verify(mockDatabaseReference).child(uid)
        verify(mockDatabaseReference).child("quotes")
        verify(mockDatabaseReference).child(quoteID)
        verify(mockDatabaseReference).setValue(mockedDatabaseQuote)
    }

    @Test
    fun `verify removeQuote will be called with proper values`() {
        given(mockDatabaseReference.child(anyString())).willReturn(mockDatabaseReference)
        viewModel.removeQuote(uid, quoteID)
        verify(mockDatabaseReference).child("users")
        verify(mockDatabaseReference).child(uid)
        verify(mockDatabaseReference).child("quotes")
        verify(mockDatabaseReference).child(quoteID)
        verify(mockDatabaseReference).removeValue()
    }
}
