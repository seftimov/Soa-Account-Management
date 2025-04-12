package mk.ukim.finki.soa.accountmanagement.infrastructure.kafka

import mk.ukim.finki.soa.accountmanagement.model.WithdrawMoneyCommand
import org.springframework.stereotype.Service


@Service
class FinanceEventTranslator() {

    fun toWithdrawMoneyCommand(event: ExpenseCreatedEventDTO): WithdrawMoneyCommand {
        return WithdrawMoneyCommand(
            accountId = event.accountId,
            amount = event.amount
        )
    }

}