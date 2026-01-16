package com.example.bookshelf.util

import org.junit.Assert.*
import org.junit.Test

class BookValidatorTest {

    @Test
    fun isValid_returnsFalse_whenTitleEmpty() {
        assertFalse(BookValidator.isValid("", "Author"))
        assertFalse(BookValidator.isValid("   ", "Author"))
    }

    @Test
    fun isValid_returnsFalse_whenAuthorEmpty() {
        assertFalse(BookValidator.isValid("Title", ""))
        assertFalse(BookValidator.isValid("Title", "   "))
    }

    @Test
    fun isValid_returnsTrue_whenBothPresent() {
        assertTrue(BookValidator.isValid("Dune", "Frank Herbert"))
    }

    @Test
    fun normalizeText_trimsSpaces() {
        assertEquals("Dune", BookValidator.normalizeText("  Dune  "))
    }

    @Test
    fun parseYear_returnsIntOrNull() {
        assertEquals(1965, BookValidator.parseYear("1965"))
        assertNull(BookValidator.parseYear(""))
        assertNull(BookValidator.parseYear("abcd"))
        assertEquals(2000, BookValidator.parseYear(" 2000 "))
    }
}
