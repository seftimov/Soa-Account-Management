package mk.ukim.finki.soa.accountmanagement.model

import jakarta.persistence.Embeddable
import java.util.*

@Embeddable
open class AccountId(value: String) : Identifier<Account>(value, Account::class.java) {
    constructor() : this(UUID.randomUUID().toString())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        return this.value == (other as AccountId).value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

