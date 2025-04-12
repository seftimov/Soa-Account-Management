package mk.ukim.finki.soa.accountmanagement.model

import org.axonframework.modelling.command.TargetAggregateIdentifier

/*-------------- Account --------------*/
data class CreateAccountCommand(
    val number: String,
    val initialBalance: Money,
)

data class WithdrawMoneyCommand(
    @TargetAggregateIdentifier
    val accountId: AccountId,
    val amount: Money
)

