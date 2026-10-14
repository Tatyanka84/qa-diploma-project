package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static final Faker faker =
            new Faker(new Locale("en"));

    private DataHelper() {
    }

    // Номера карт

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    // Валидные данные

    public static String getValidMonth() {
        return LocalDate.now()
                .format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getValidYear(int yearOffset) {
        return LocalDate.now()
                .plusYears(yearOffset)
                .format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getValidHolder() {
        return faker.name().firstName()
                + " "
                + faker.name().lastName();
    }

    public static String getValidCVCCVV() {
        return "123";
    }

    // Полностью валидные данные

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo(
                getDeclinedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    // Номер карты

    public static CardInfo getCardNumberIfEmpty() {
        return new CardInfo(
                "",
                getValidMonth(),
                getValidYear(2),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getCardNumberOfFifteenDigits() {
        return new CardInfo(
                "444444444444444",
                getValidMonth(),
                getValidYear(6),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getCardNumberOfSixteenDigits() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getCardNumberOfSeventeenDigits() {
        return new CardInfo(
                "44444444444444414",
                getValidMonth(),
                getValidYear(5),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getCardNumberWithSpecialSymbols() {
        return new CardInfo(
                "@#&",
                getValidMonth(),
                getValidYear(6),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getCardNumberNotRegistered() {
        return new CardInfo(
                "1234567891011121",
                getValidMonth(),
                getValidYear(5),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    // Месяц

    public static CardInfo getMonthIfEmpty() {
        return new CardInfo(
                getApprovedCardNumber(),
                "",
                getValidYear(3),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getMonthDoubleZero() {
        return new CardInfo(
                getApprovedCardNumber(),
                "00",
                getValidYear(3),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getMonthMinimumValid() {
        return new CardInfo(
                getApprovedCardNumber(),
                "01",
                getValidYear(3),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getMonthMaximumValid() {
        return new CardInfo(
                getApprovedCardNumber(),
                "12",
                getValidYear(3),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getMonthIfNotExist() {
        return new CardInfo(
                getApprovedCardNumber(),
                "13",
                getValidYear(3),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    // Месяц из трёх цифр.


    public static CardInfo getMonthOfThreeDigits() {
        return new CardInfo(
                getApprovedCardNumber(),
                "123",
                getValidYear(3),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    // Год

    public static CardInfo getYearIfEmpty() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                "",
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getCurrentYear() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(0),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getLastYear() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(-1),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    public static CardInfo getYearTenYearsMore() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(10),
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    // Год из трёх цифр.


    public static CardInfo getYearOfThreeDigits() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                "234",
                getValidHolder(),
                getValidCVCCVV()
        );
    }

    // Владелец

    public static CardInfo getHolderIfEmpty() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                "",
                getValidCVCCVV()
        );
    }

    public static CardInfo getHolderOfOneLetter() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                "N",
                getValidCVCCVV()
        );
    }

    public static CardInfo getHolderOfTwoLetters() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                "IO",
                getValidCVCCVV()
        );
    }

    public static CardInfo getHolderWithCyrillic() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                "Мария Полежаева",
                getValidCVCCVV()
        );
    }

    public static CardInfo getHolderWithSpecialSymbols() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                "@#",
                getValidCVCCVV()
        );
    }

    public static CardInfo getHolderWithDigits() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                "23445",
                getValidCVCCVV()
        );
    }


    public static CardInfo getHolderWithHyphen() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                "Anna-Marie Smith",
                getValidCVCCVV()
        );
    }


    // CVC/CVV

    public static CardInfo getCVCCVVIfEmpty() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                getValidHolder(),
                ""
        );
    }

    public static CardInfo getCVCCVVOnTwoDigits() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                getValidHolder(),
                "12"
        );
    }

    public static CardInfo getCVCCVVOnThreeDigits() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                getValidHolder(),
                "123"
        );
    }

    // CVC из четырёх цифр.


    public static CardInfo getCVCCVVOnFourDigits() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                getValidHolder(),
                "1234"
        );
    }

    public static CardInfo getCVCCVVWithSpecialSymbols() {
        return new CardInfo(
                getApprovedCardNumber(),
                getValidMonth(),
                getValidYear(3),
                getValidHolder(),
                "@#&"
        );
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String holder;
        String CVCCVV;
    }
}