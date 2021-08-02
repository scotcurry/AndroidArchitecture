package org.curryware.androidarchitecture

import android.util.Log
import io.mockk.every
import kotlinx.coroutines.runBlocking
import org.curryware.androidarchitecture.repository.AccessRepository
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import io.mockk.mockkStatic

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_get_auth_token() = runBlocking {

        val repository = AccessRepository()
        val accessToken = repository.getAccessToken()
        Assert.assertEquals(accessToken, 10)
    }
}
