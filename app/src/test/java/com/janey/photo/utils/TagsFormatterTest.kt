package com.janey.photo.utils

import junit.framework.Assert.assertEquals
import org.junit.Test

class TagsFormatterTest {
    @Test
    fun `given list of tags, # is prepended to each one`() {
        val rawTags = "yorkshire trees moretags tagfour somemoretags"

        val formattedTags = rawTags.formatTags()

        assertEquals("#yorkshire #trees #moretags #tagfour #somemoretags", formattedTags)
    }
}