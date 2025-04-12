package mk.ukim.finki.soa.accountmanagement.service.impl

import mk.ukim.finki.soa.accountmanagement.model.AccountId
import mk.ukim.finki.soa.accountmanagement.model.CreateAccountCommand
import mk.ukim.finki.soa.accountmanagement.model.WithdrawMoneyCommand
import mk.ukim.finki.soa.accountmanagement.service.AccountModificationService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class AccountModificationServiceImpl(
    val commandGateway: CommandGateway,
) : AccountModificationService {
    override fun createAccount(command: CreateAccountCommand): CompletableFuture<AccountId> {
        return commandGateway.send(command);
    }

    override fun withdrawMoney(command: WithdrawMoneyCommand): CompletableFuture<AccountId> {
        return commandGateway.send(command);
    }

}

