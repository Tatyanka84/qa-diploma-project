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

@DisplayName("Тесты оплаты дебетовой картой")
public class DebitCardTest {

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
    public void setUp() {
        String baseUrl = System.getProperty("sut.url", "http://localhost:8080");
        SQLHelper.clearPaymentTable();
        SQLHelper.clearCreditTable();
        open(baseUrl);
    }

    // ПОЗИТИВНЫЕ ТЕСТЫ

    @Test
    @DisplayName("Успешная оплата дебетовой картой (APPROVED)")
    void shouldCardPaymentApproved() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getApprovedCardInfo());
        form.paymentApproved();

        assertEquals(
                "APPROVED",
                SQLHelper.getPaymentStatus()
        );
    }

    @Test
    @DisplayName("Оплата отклонённой картой (DECLINED)")
    void shouldCardPaymentDeclined() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getDeclinedCardInfo());
        form.paymentDeclined();

        assertEquals(
                "DECLINED",
                SQLHelper.getPaymentStatus()
        );
    }

    // НОМЕР КАРТЫ

    @Test
    @DisplayName("Пустое поле номера карты")
    void shouldCardIfEmpty() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }

    @Test
    @DisplayName("Номер карты из 15 цифр")
    void shouldCardNumberOfFifteenDigits() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getCardNumberOfFifteenDigits()
        );
        form.incorrectCardNumberVisible();
    }

    @Test
    @DisplayName("Номер карты из 16 цифр")
    void shouldCardNumberOfSixteenDigits() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getCardNumberOfSixteenDigits()
        );
        form.paymentApproved();
    }

    @Test
    @DisplayName("Номер карты со специальными символами")
    void shouldCardWithSpecialSymbols() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getCardNumberWithSpecialSymbols()
        );
        form.incorrectCardNumberVisible();
    }

    @Test
    @DisplayName("Незарегистрированная карта")
    void shouldCardNumberNotRegistered() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getCardNumberNotRegistered()
        );
        //form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    // МЕСЯЦ

    @Test
    @DisplayName("Пустое поле месяца")
    void shouldMonthIfEmpty() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible("Неверный формат");
    }

    @Test
    @DisplayName("Месяц 00")
    void shouldMonthDoubleZero() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getMonthDoubleZero());
        form.incorrectMonthVisible(
                "Неверно указан срок действия карты"
        );
    }

    @Test
    @DisplayName("Месяц 01")
    void shouldMonthMinimumValid() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getMonthMinimumValid());
        form.paymentApproved();
    }

    @Test
    @DisplayName("Месяц 12")
    void shouldMonthMaximumValid() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getMonthMaximumValid());
        form.paymentApproved();
    }

    @Test
    @DisplayName("Месяц 13")
    void shouldMonthIfNotExist() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible(
                "Неверно указан срок действия карты"
        );
    }

    // ГОД

    @Test
    @DisplayName("Пустое поле года")
    void shouldYearIfEmpty() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getYearIfEmpty());
        form.incorrectYearVisible("Неверный формат");
    }

    @Test
    @DisplayName("Текущий год")
    void shouldCurrentYear() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getCurrentYear());
        form.incorrectYearVisible(
                "Истёк срок действия карты"
        );
    }

    @Test
    @DisplayName("Прошедший год")
    void shouldLastYear() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible(
                "Истёк срок действия карты"
        );
    }

    @Test
    @DisplayName("Год через 10 лет")
    void shouldYearTenYearsMore() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getYearTenYearsMore());
        form.paymentApproved();
    }

    // ВЛАДЕЛЕЦ

    @Test
    @DisplayName("Пустое поле владельца")
    void shouldHolderIfEmpty() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Владелец из одной буквы")
    void shouldHolderOfOneLetter() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getHolderOfOneLetter()
        );
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец из двух букв")
    void shouldHolderOfTwoLetters() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getHolderOfTwoLetters()
        );
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец кириллицей")
    void shouldHolderWithCyrillic() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getHolderWithCyrillic()
        );
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец со специальными символами")
    void shouldHolderWithSpecialSymbols() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getHolderWithSpecialSymbols()
        );
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец цифрами при оплате картой")
    void shouldHolderWithDigitsByCard() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible("Неверный формат");
    }

    @Test
    @DisplayName("Владелец с дефисом при оплате картой")
    void shouldHolderWithHyphenByCard() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getHolderWithHyphen());
        form.paymentApproved();

        assertEquals(
                "APPROVED",
                SQLHelper.getPaymentStatus()
        );
    }


    // CVC/CVV

    @Test
    @DisplayName("Пустое поле CVC")
    void shouldCVCCVVIfEmpty() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }

    @Test
    @DisplayName("CVC из двух цифр")
    void shouldCVCCVVOnTwoDigit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getCVCCVVOnTwoDigits()
        );
        form.incorrectCodeVisible();
    }

    @Test
    @DisplayName("CVC из трёх цифр")
    void shouldCVCCVVOnThreeDigit() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getCVCCVVOnThreeDigits()
        );
        form.paymentApproved();
    }

    @Test
    @DisplayName("CVC со специальными символами")
    void shouldCVCCVVWithSpecialSymbols() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();

        form.completedForm(
                DataHelper.getCVCCVVWithSpecialSymbols()
        );
        form.incorrectCodeVisible();
    }

    // ПУСТАЯ ФОРМА

    @Test
    @DisplayName("Отправка пустой формы при оплате картой")
    void shouldFormIfEmptyByCard() {
        var mainPage = new MainPage();
        var form = mainPage.buyByCard();
        form.completedForm(new DataHelper.CardInfo("", "", "", "", ""));
        form.incorrectCardNumberVisible();

        form.incorrectMonthVisible("Неверный формат");
        form.incorrectYearVisible("Неверный формат");
        form.incorrectHolderVisible("Поле обязательно для заполнения");
        form.incorrectCodeVisible();
    }
}




