package com.inventi.k8sproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class K8sProjectApplication

fun main(args: Array<String>) {
	runApplication<K8sProjectApplication>(*args)
}
