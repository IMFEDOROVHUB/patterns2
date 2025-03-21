import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;

public class AuthTest {

    @BeforeEach
    public void setUp() {
        Selenide.open("http://localhost:9999");
    }


    @Test
    public void successAuth() {
        var registredUser = DataGenerator.Registration.getRegistredUser("active");

        $("[data-test-id='login'] input").setValue(registredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    public void blockedUser() {
        var registredUser = DataGenerator.Registration.getRegistredUser("blocked");

        $("[data-test-id='login'] input").setValue(registredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    public void notRegistredUser() {
        var registredUser = DataGenerator.Registration.getUser("active");

        $("[data-test-id='login'] input").setValue(registredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    public void wrongPassword() {
        var registredUser = DataGenerator.Registration.getRegistredUser("active");
        var password = DataGenerator.getRandomPassword();

        $("[data-test-id='login'] input").setValue(registredUser.getLogin());
        $("[data-test-id='password'] input").setValue(password);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}
