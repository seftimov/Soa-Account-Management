package mk.ukim.finki.soa.accountmanagement.model

/*-------------- Account --------------*/
data class AccountCreatedEvent(
    val accountId: AccountId,
    val number: String,
    val balance: Money
) {
    constructor(command: CreateAccountCommand) : this(
        accountId = AccountId(),
        number = command.number,
        balance = command.initialBalance
    )
}

data class MoneyWithdrawnEvent(
    val accountId: AccountId,
    val amount: Money
) {
    constructor(command: WithdrawMoneyCommand) : this(
        accountId = command.accountId,
        amount = command.amount
    )
}