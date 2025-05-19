package mk.ukim.finki.soa.accountmanagement.pact;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.v3.messaging.Message;
import au.com.dius.pact.model.v3.messaging.MessagePact;
import mk.ukim.finki.soa.accountmanagement.infrastructure.KafkaEventConsumer;
import mk.ukim.finki.soa.accountmanagement.infrastructure.kafka.ExpenseCreatedEventDTO;
import mk.ukim.finki.soa.accountmanagement.infrastructure.kafka.FinanceEventTranslator;
import mk.ukim.finki.soa.accountmanagement.model.AccountId;
import mk.ukim.finki.soa.accountmanagement.model.Money;
import mk.ukim.finki.soa.accountmanagement.model.WithdrawMoneyCommand;
import mk.ukim.finki.soa.accountmanagement.service.AccountModificationService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(value = {PactConsumerTestExt.class, MockitoExtension.class})
@PactTestFor(providerName = "finance_provider", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V3)
public class ExpenseCreatedListenerTest {
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String KEY_CONTENT_TYPE = "contentType";

    @Mock
    private AccountModificationService accountModificationService;

    @Mock
    private FinanceEventTranslator financeEventTranslator;

    @InjectMocks
    private KafkaEventConsumer kafkaEventConsumer;

    @Pact(consumer = "accounts_consumer")
    MessagePact expenseCreatedPact(MessagePactBuilder builder) {
        PactDslJsonBody jsonBody = new PactDslJsonBody()
                .object("expenseId")
                .stringValue("value", "Expense:00000000-0000-0000-0000-000000000000")
                .closeObject()
                .object("accountId")
                .stringValue("value", "Account:00000000-0000-0000-0000-000000000000")
                .closeObject()
                .object("amount")
                .numberType("amount", 100.5)
                .stringType("currency", "EUR")
                .closeObject()
                .asBody();

        return builder.expectsToReceive("Expense Created Event")
                .withContent(jsonBody)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "expenseCreatedPact", providerType = ProviderType.ASYNCH)
    void testExpenseCreatedPact(List<Message> messages) {
        AccountId accountId = new AccountId("Account:00000000-0000-0000-0000-000000000000");
        WithdrawMoneyCommand cmd = new WithdrawMoneyCommand(
                accountId,
                new Money(100.5, "EUR")
        );

        when(financeEventTranslator.toWithdrawMoneyCommand(any(ExpenseCreatedEventDTO.class)))
                .thenReturn(cmd);

        when(accountModificationService.withdrawMoney(
                new WithdrawMoneyCommand(
                        new AccountId("Account:00000000-0000-0000-0000-000000000000"),
                        new Money(100.5, "EUR")
                )
        )).thenReturn(CompletableFuture.completedFuture(new AccountId("Account:00000000-0000-0000-0000-000000000000")));

        messages.forEach(message -> {
            assertDoesNotThrow(() -> {
                String json = new String(message.contentsAsBytes(), StandardCharsets.UTF_8);
                ConsumerRecord<String, String> record = new ConsumerRecord<>("expense.created", 0, 0L, null, json);

                assertDoesNotThrow(() -> kafkaEventConsumer.listen(record));
            });


            verify(accountModificationService, times(1)).withdrawMoney(cmd);
        });
    }
}
