package mk.ukim.finki.soa.accountmanagement.infrastructure.kafka

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mk.ukim.finki.soa.accountmanagement.model.AccountId
import mk.ukim.finki.soa.accountmanagement.model.Money
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExpenseCreatedEventDTO(
    val expenseId: ExpenseIdDTO,
    val amount: Money,
    val accountId: AccountId,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExpenseIdDTO(val value: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class VendorIdDTO(val value: String)
