package mk.ukim.finki.soa.accountmanagement.model

data class CreateAccountCommandDTO(
    val number: String,
    val initialBalance: Money,
)

data class WithdrawMoneyCommandDTO(
    val accountId: AccountId,
    val amount: Money
)