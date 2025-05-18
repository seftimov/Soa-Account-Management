package mk.ukim.finki.soa.accountmanagement.web

import jakarta.servlet.http.HttpServletRequest
import mk.ukim.finki.soa.accountmanagement.model.AccountId
import mk.ukim.finki.soa.accountmanagement.model.AccountView
import mk.ukim.finki.soa.accountmanagement.service.AccountViewReadService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
class AccountRestApi(private val accountViewReadService: AccountViewReadService) {
    @GetMapping("/exists/{id}")
    fun existsAccount(@PathVariable id: AccountId, req: HttpServletRequest): Boolean {
        return this.accountViewReadService.existsAccount(id)
    }

    @GetMapping("/{id}")
    fun getAccountById(@PathVariable id: AccountId): AccountView? {
        return this.accountViewReadService.getAccountById(id)
    }
}