package validator.customvalidator.custom_validator.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import validator.customvalidator.custom_validator.model.PostClientRequest

@RestController
@RequestMapping("clients")
class ClientController {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ClientController::class.java)
    }

    @GetMapping
    fun fetchPostInfo(): String {
        return "post info"
    }

    /**
     * 通常のPOSTリクエスト(application/json)
     */
    @PostMapping
    fun postClientInfo(
        @RequestBody request: PostClientRequest
    ): ResponseEntity<Any> {
        LOGGER.info("postClient request: ${request}")
        request.validate()
        return ResponseEntity.ok("Client created.")
    }

    /**
     * MultipartFile付きのPOSTリクエスト(multipart/form-data)
     * - ここでmultipart/form-data, JSのFormObjectについて学習したい
     */
}
