package com.janey.photo.utils

import junit.framework.Assert.assertEquals
import org.junit.Test

class ProfileUrlFormatterTest {
    @Test
    fun `test profile url is correctly formatted`() {
        assertEquals(
            "https://farm4598.staticflickr.com/omnesque/buddyicons/tortor.jpg",
            formatProfileUrl(4598, "omnesque", "tortor")
        )
    }
}