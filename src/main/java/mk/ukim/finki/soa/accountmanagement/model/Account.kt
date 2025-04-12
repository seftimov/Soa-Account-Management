package mk.ukim.finki.soa.accountmanagement.model

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate(repository = "axonAccountRepository")
@Entity
class Account : LabeledEntity {
    @AggregateIdentifier
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    private lateinit var id: AccountId

    private lateinit var number: String

    private lateinit var balance: Money

    @CommandHandler
    constructor(command: CreateAccountCommand) {
        val event = AccountCreatedEvent(command)

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: AccountCreatedEvent) {
        this.id = event.accountId
        this.number = event.number
        this.balance = event.balance
    }

    @CommandHandler
    fun handle(command: WithdrawMoneyCommand) {
        if (!balance.isGreaterThanOrEqual(command.amount)) {
            throw IllegalArgumentException("Insufficient funds")
        }

        val event = MoneyWithdrawnEvent(
            accountId = this.id,
            amount = command.amount
        )

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: MoneyWithdrawnEvent) {
        this.balance = this.balance.subtract(event.amount)
    }

    override fun getId(): Identifier<out Any> {
        return this.id
    }

    override fun getLabel(): String {
        return this.id.toString()
    }
}
