package com.puce.invoices

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class InvoicesApplication

fun main(args: Array<String>) {
	runApplication<InvoicesApplication>(*args)
}
