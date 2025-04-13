package mk.ukim.finki.soa.accountmanagement.service.impl

import mk.ukim.finki.soa.accountmanagement.model.AccountId
import mk.ukim.finki.soa.accountmanagement.repository.AccountViewJpaRepository
import mk.ukim.finki.soa.accountmanagement.service.AccountViewReadService
import org.springframework.stereotype.Service

@Service
class AccountViewReadServiceImpl(val accountViewJpaRepository: AccountViewJpaRepository) : AccountViewReadService {
    override fun existsAccount(accountId: AccountId): Boolean {
        return accountViewJpaRepository.existsById(accountId)
    }
}