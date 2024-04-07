package com.inventi.k8sproject

import org.springframework.stereotype.Service

private const val FACTORIAL_NUMBER = 20L

@Service
class Service: IService {

    override fun calculateFactorial() =
        calculate(FACTORIAL_NUMBER)

    private fun calculate(number: Long): Long =
        if (number == 0L) {
            1L
        } else {
            number * calculate(number - 1)
        }

}