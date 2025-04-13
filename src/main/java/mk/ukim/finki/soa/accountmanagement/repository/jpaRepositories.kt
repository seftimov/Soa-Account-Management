package mk.ukim.finki.soa.accountmanagement.repository

import mk.ukim.finki.soa.accountmanagement.model.AccountId
import mk.ukim.finki.soa.accountmanagement.model.AccountView
import org.springframework.data.jpa.repository.JpaRepository

interface AccountViewJpaRepository : JpaRepository<AccountView, AccountId>