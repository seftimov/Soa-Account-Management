package mk.ukim.finki.soa.accountmanagement.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import mk.ukim.finki.soa.accountmanagement.infrastructure.kafka.ExpenseCreatedEventDTO
import mk.ukim.finki.soa.accountmanagement.infrastructure.kafka.FinanceEventTranslator
import mk.ukim.finki.soa.accountmanagement.service.AccountModificationService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaEventConsumer(
    private val financeEventTranslator: FinanceEventTranslator,
    private val accountModificationService: AccountModificationService
) {

    val objectMapper = ObjectMapper()
        .registerModules(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @KafkaListener(topics = ["expense.created"])
    fun listen(record: ConsumerRecord<String, String>) {

        try {
            val eventDTO: ExpenseCreatedEventDTO = objectMapper.readValue(record.value())

            val command = financeEventTranslator.toWithdrawMoneyCommand(eventDTO);

            accountModificationService.withdrawMoney(command);
        } catch (ex: Exception) {
            println(ex.message)
        }

    }

}
