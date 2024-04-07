package com.inventi.k8sproject

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    private val service: IService
) {

    @GetMapping("/calculate")
    fun calculate(): String {
        val factorial = service.calculateFactorial()
        return "Calculated factorial: $factorial "
    }

}