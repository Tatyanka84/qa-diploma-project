package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    private final SelenideElement paymentByCard = $(byText("Оплата по карте"));
    private final SelenideElement creditByCard = $(byText("Кредит по данным карты"));

    private final SelenideElement cardNumber =
            $$(".form-field").findBy(text("Номер карты")).find(".input__control");

    private final SelenideElement month =
            $$(".form-field .input-group__input-case").findBy(text("Месяц")).find(".input__control");

    private final SelenideElement year =
            $$(".form-field .input-group__input-case").findBy(text("Год")).find(".input__control");

    private final SelenideElement holder =
            $$(".form-field .input-group__input-case").findBy(text("Владелец")).find(".input__control");

    private final SelenideElement cvccvv =
            $$(".form-field .input-group__input-case").findBy(text("CVC/CVV")).find(".input__control");

    private final SelenideElement button = $$(".button__text").findBy(text("Продолжить"));

    // Ошибки полей
    private final SelenideElement cardNumberError =
            $$(".form-field").findBy(text("Номер карты")).find(".input__sub");

    private final SelenideElement monthError =
            $$(".form-field .input-group__input-case").findBy(text("Месяц")).find(".input__sub");

    private final SelenideElement yearError =
            $$(".form-field .input-group__input-case").findBy(text("Год")).find(".input__sub");

    private final SelenideElement holderError =
            $$(".form-field .input-group__input-case").findBy(text("Владелец")).find(".input__sub");

    private final SelenideElement cvccvvError =
            $$(".form-field .input-group__input-case").findBy(text("CVC/CVV")).find(".input__sub");

    private final SelenideElement successNotification = $(".notification_status_ok .notification__content");
    private final SelenideElement errorNotification = $(".notification_status_error .notification__content");

    public PaymentPage() {
        paymentByCard.shouldBe(visible, Duration.ofSeconds(5));
    }

    public PaymentPage(boolean isCredit) {
        if (isCredit) {
            creditByCard.shouldBe(visible, Duration.ofSeconds(5));
        } else {
            paymentByCard.shouldBe(visible, Duration.ofSeconds(5));
        }
    }

    public void completedForm(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        holder.setValue(cardInfo.getHolder());
        cvccvv.setValue(cardInfo.getCVCCVV());
        button.click();
    }

    public void paymentApproved() {
        successNotification
                .should(Condition.text("Операция одобрена Банком."), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    public void paymentDeclined() {
        errorNotification
                .should(Condition.text("Банк отказал в проведении операции."), Duration.ofSeconds(15))
                .shouldBe(visible);
    }

    public void incorrectCardNumberVisible() {
        cardNumberError
                .should(Condition.text("Неверный формат"), Duration.ofSeconds(5))
                .shouldBe(visible);
    }

    public void incorrectMonthVisible(String expectedText) {
        monthError
                .should(Condition.text(expectedText), Duration.ofSeconds(5))
                .shouldBe(visible);
    }

    public void incorrectYearVisible(String expectedText) {
        yearError
                .should(Condition.text(expectedText), Duration.ofSeconds(5))
                .shouldBe(visible);
    }

    public void incorrectHolderVisible(String expectedText) {
        holderError
                .should(Condition.text(expectedText), Duration.ofSeconds(5))
                .shouldBe(visible);
    }

    public void incorrectCodeVisible() {
        cvccvvError
                .should(Condition.text("Неверный формат"), Duration.ofSeconds(5))
                .shouldBe(visible);
    }


    public void submitForm() {
        button.click();

    }
}
