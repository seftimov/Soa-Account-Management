package mk.ukim.finki.soa.accountmanagement.pact;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import mk.ukim.finki.soa.accountmanagement.model.AccountId;
import mk.ukim.finki.soa.accountmanagement.model.AccountView;
import mk.ukim.finki.soa.accountmanagement.model.Money;
import mk.ukim.finki.soa.accountmanagement.service.AccountViewReadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("accounts_provider")
@PactFolder("pacts") // Path to the directory containing the JSON pact file
public class PactAccountsProviderTest {

    @LocalServerPort
    int port;

    @MockitoBean
    AccountViewReadService accountViewReadService;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("Account with ID Account:00000000-0000-0000-0000-000000000000 exists")
    public void accountExists() {
        AccountView accountView = new AccountView(
                new AccountId("Account:00000000-0000-0000-0000-000000000000"),
                "123456789",
                new Money(100.5, "EUR")
        );

        when(accountViewReadService.existsAccount(new AccountId("Account:00000000-0000-0000-0000-000000000000"))).thenReturn(true);
        when(accountViewReadService.getAccountById(new AccountId("Account:00000000-0000-0000-0000-000000000000"))).thenReturn(accountView);
    }
}
