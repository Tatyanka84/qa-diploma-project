package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты оплаты в кредит")
public class CreditCardTest {


    @BeforeAll
    static void setUpAll() {
        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Selenium\\chromedriver\\win64\\154.0.8037.92\\chromedriver.exe"
        );

        SelenideLogger.addListener(
                "allure",
                new AllureSelenide()
        );
    }


    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        SQLHelper.clearPaymentTable();
        SQLHelper.clearCreditTable();
        //open("http://localhost:8080");
        open(System.getProperty("sut.url"));
    }

    // ПОЗИТИВНЫЕ ТЕСТЫ

    @Test
    @DisplayName("Успешная покупка в кредит (APPROVED)")
    void shouldCreditPaymentApproved() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(DataHelper.getApprovedCardInfo());
        form.paymentApproved();

        assertEquals(
                "APPROVED",
                SQLHelper.getCreditRequestStatus()
        );
    }

    @Test
    @DisplayName("Отказ при покупке в кредит (DECLINED)")
    void shouldCreditPaymentDeclined() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(DataHelper.getDeclinedCardInfo());
        form.paymentDeclined();

        assertEquals(
                "DECLINED",
                SQLHelper.getCreditRequestStatus()
        );
    }

    // НОМЕР КАРТЫ

    @Test
    @DisplayName("Пустое поле номера карты при покупке в кредит")
    void shouldCardIfEmptyByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCardNumberIfEmpty()
        );
        form.incorrectCardNumberVisible();
    }

    @Test
    @DisplayName("Номер карты из 15 цифр при покупке в кредит")
    void shouldCardNumberOfFifteenDigitsByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCardNumberOfFifteenDigits()
        );
        form.incorrectCardNumberVisible();
    }

    @Test
    @DisplayName("Номер карты из 16 цифр при покупке в кредит")
    void shouldCardNumberOfSixteenDigitsByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCardNumberOfSixteenDigits()
        );
        form.paymentApproved();
    }

    @Test
    @DisplayName("Номер карты со специальными символами при покупке в кредит")
    void shouldCardWithSpecialSymbolsByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCardNumberWithSpecialSymbols()
        );
        form.incorrectCardNumberVisible();
    }

    @Test
    @DisplayName("Незарегистрированная карта при покупке в кредит")
    void shouldCardNumberNotRegisteredByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCardNumberNotRegistered()
        );
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }

    // МЕСЯЦ

    @Test
    @DisplayName("Пустое поле месяца при покупке в кредит")
    void shouldMonthIfEmptyByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getMonthIfEmpty()
        );
        form.incorrectMonthVisible("Неверный формат");
    }

    @Test
    @DisplayName("Месяц 00 при покупке в кредит")
    void shouldMonthDoubleZeroByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getMonthDoubleZero()
        );
        form.incorrectMonthVisible(
                "Неверно указан срок действия карты"
        );
    }

    @Test
    @DisplayName("Месяц 01 при покупке в кредит")
    void shouldMonthMinimumValidByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getMonthMinimumValid()
        );
        form.paymentApproved();
    }

    @Test
    @DisplayName("Месяц 12 при покупке в кредит")
    void shouldMonthMaximumValidByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getMonthMaximumValid()
        );
        form.paymentApproved();
    }

    @Test
    @DisplayName("Месяц 13 при покупке в кредит")
    void shouldMonthIfNotExistByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getMonthIfNotExist()
        );
        form.incorrectMonthVisible(
                "Неверно указан срок действия карты"
        );
    }

    // ГОД

    @Test
    @DisplayName("Пустое поле года при покупке в кредит")
    void shouldYearIfEmptyByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getYearIfEmpty()
        );
        form.incorrectYearVisible("Неверный формат");
    }

    @Test
    @DisplayName("Текущий год при покупке в кредит")
    void shouldCurrentYearByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCurrentYear()
        );
        form.incorrectYearVisible(
                "Истёк срок действия карты"
        );
    }

    @Test
    @DisplayName("Прошедший год при покупке в кредит")
    void shouldLastYearByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getLastYear()
        );
        form.incorrectYearVisible(
                "Истёк срок действия карты"
        );
    }

    @Test
    @DisplayName("Год через 10 лет при покупке в кредит")
    void shouldYearTenYearsMoreByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getYearTenYearsMore()
        );
        form.paymentApproved();
    }

    // ВЛАДЕЛЕЦ

    @Test
    @DisplayName("Пустое поле владельца при покупке в кредит")
    void shouldHolderIfEmptyByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getHolderIfEmpty()
        );
        form.incorrectHolderVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Владелец из одной буквы при покупке в кредит")
    void shouldHolderOfOneLetterByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getHolderOfOneLetter()
        );
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец из двух букв при покупке в кредит")
    void shouldHolderOfTwoLettersByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getHolderOfTwoLetters()
        );
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец кириллицей при покупке в кредит")
    void shouldHolderWithCyrillicByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getHolderWithCyrillic()
        );
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец со специальными символами при покупке в кредит")
    void shouldHolderWithSpecialSymbolsByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getHolderWithSpecialSymbols()
        );
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец цифрами при покупке в кредит")
    void shouldHolderWithDigitsByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец с дефисом при покупке в кредит")
    void shouldHolderWithHyphenByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(DataHelper.getHolderWithHyphen());
        form.paymentApproved();

        assertEquals(
                "APPROVED",
                SQLHelper.getCreditRequestStatus()
        );
    }


    // CVC/CVV

    @Test
    @DisplayName("Пустое поле CVC при покупке в кредит")
    void shouldCVCCVVIfEmptyByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCVCCVVIfEmpty()
        );
        form.incorrectCodeVisible();
    }

    @Test
    @DisplayName("CVC из двух цифр при покупке в кредит")
    void shouldCVCCVVOnTwoDigitsByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCVCCVVOnTwoDigits()
        );
        form.incorrectCodeVisible();
    }

    @Test
    @DisplayName("CVC из трёх цифр при покупке в кредит")
    void shouldCVCCVVOnThreeDigitsByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCVCCVVOnThreeDigits()
        );
        form.paymentApproved();
    }

    @Test
    @DisplayName("CVC со специальными символами при покупке в кредит")
    void shouldCVCCVVWithSpecialSymbolsByCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(
                DataHelper.getCVCCVVWithSpecialSymbols()
        );
        form.incorrectCodeVisible();
    }

    // ПУСТАЯ ФОРМА

    @Test
    @DisplayName("Отправка пустой формы при покупке в кредит")
    void shouldFormIfEmptyForCredit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCreditCard();

        form.completedForm(new DataHelper.CardInfo("", "", "", "", ""));

        form.incorrectCardNumberVisible();
        form.incorrectMonthVisible("Неверный формат");
        form.incorrectYearVisible("Неверный формат");
        form.incorrectHolderVisible("Поле обязательно для заполнения");
        form.incorrectCodeVisible();
    }
}








    