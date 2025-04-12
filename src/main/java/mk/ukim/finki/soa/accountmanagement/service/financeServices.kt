package mk.ukim.finki.soa.accountmanagement.service

import mk.ukim.finki.soa.accountmanagement.model.AccountId
import mk.ukim.finki.soa.accountmanagement.model.CreateAccountCommand
import mk.ukim.finki.soa.accountmanagement.model.WithdrawMoneyCommand
import java.util.concurrent.CompletableFuture

interface AccountModificationService {
    fun createAccount(command: CreateAccountCommand): CompletableFuture<AccountId>

    fun withdrawMoney(command: WithdrawMoneyCommand): CompletableFuture<AccountId>
}

