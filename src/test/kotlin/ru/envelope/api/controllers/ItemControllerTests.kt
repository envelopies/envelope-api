package ru.envelope.api.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import ru.envelope.api.PostgresTestcontainersConfiguration

@AutoConfigureMockMvc
@Import(PostgresTestcontainersConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `test items list`() {
        mockMvc.get("/v1/items") {

        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("""[]""") }
        }
    }
}
