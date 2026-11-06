package com.wannaphong.hostai

import io.javalin.Javalin
import org.eclipse.jetty.server.ServerConnector
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalBindingTest {

    @Test
    fun testBindHostIsLoopback() {
        assertEquals("127.0.0.1", OpenAIApiServer.BIND_HOST)
    }

    @Test
    fun testDefaultPortIs8085() {
        assertEquals(8085, SettingsManager.DEFAULT_PORT)
        assertEquals(8085, ApiServerService.DEFAULT_PORT)
    }

    @Test
    fun testJavalinBindsConnectorToBindHost() {
        // Same call pattern as OpenAIApiServer.start(); port 0 = any free port
        val app = Javalin.create { config -> config.showJavalinBanner = false }
            .start(OpenAIApiServer.BIND_HOST, 0)
        try {
            val connector = app.jettyServer().server().connectors[0] as ServerConnector
            assertEquals("127.0.0.1", connector.host)
        } finally {
            app.stop()
        }
    }
}
