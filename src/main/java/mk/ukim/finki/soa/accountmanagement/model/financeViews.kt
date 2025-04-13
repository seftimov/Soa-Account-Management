package mk.ukim.finki.soa.accountmanagement.model

import jakarta.persistence.*
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "account")
@Immutable
data class AccountView(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: AccountId,

    val number: String,

    val balance: Money
) : LabeledEntity {
    override fun getId(): Identifier<out Any> {
        return id
    }

    override fun getLabel(): String {
        return number
    }
}