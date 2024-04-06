package com.inventi.k8sproject

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping("/hello")
    fun hello(): String {
        return "Hello, World!"
    }

}