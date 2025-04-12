package mk.ukim.finki.soa.accountmanagement.web

import mk.ukim.finki.soa.accountmanagement.model.CreateAccountCommand
import mk.ukim.finki.soa.accountmanagement.model.CreateAccountCommandDTO
import mk.ukim.finki.soa.accountmanagement.model.WithdrawMoneyCommand
import mk.ukim.finki.soa.accountmanagement.model.WithdrawMoneyCommandDTO
import mk.ukim.finki.soa.accountmanagement.service.AccountModificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/submitCommand")
class AccountCommandDispatcherRestApi(
    val accountModificationService: AccountModificationService,
) {
    @PostMapping("/CreateAccountCommand")
    fun createAccount(
        @RequestBody commandDto: CreateAccountCommandDTO
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(
            accountModificationService.createAccount(
                CreateAccountCommand(
                    number = commandDto.number,
                    initialBalance = commandDto.initialBalance,
                )
            )
        )
    }

    @PostMapping("/WithdrawMoneyCommand")
    fun withdrawMoney(
        @RequestBody commandDto: WithdrawMoneyCommandDTO
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(
            accountModificationService.withdrawMoney(
                WithdrawMoneyCommand(
                    accountId = commandDto.accountId,
                    amount = commandDto.amount
                )
            )
        )
    }
}