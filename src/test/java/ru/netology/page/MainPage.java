package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement buy = $(byText("Купить"));
    private final SelenideElement buyOnCredit = $(byText("Купить в кредит"));

    public PaymentPage buyByCard() {
        buy.click();
        return new PaymentPage();
    }

    public PaymentPage buyByCreditCard() {
        buyOnCredit.click();
        return new PaymentPage(true);
    }
}


