package qa.quru;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import qa.quru.domain.MenuItem;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.*;

public class WebTest {

    @BeforeEach
    void setup() {
        Selenide.open("https://simplewine.ru/");
        sleep(1500);
        element(byClassName("js-age-confirm")).shouldBe(visible).click();
        element(byClassName("ui-location__button")).click();
    }

    @CsvSource(value = {
            "ВИНО, Максимально полное собрание сочинений на тему вина",
            "КОНЬЯК, Коллекция коньяков от первых Домов Франции"
    })
    @ParameterizedTest(name = "Проверка перехода в нужный раздел каталога по названию {0}")
    void simpleMenuTest(String testData, String expectedResult) {
        List<SelenideElement> menuList = elements(byClassName("navigation__link-text"));
        menuList.stream().filter(it -> it.getText().equals(testData)).findFirst().get().click();
        element(byClassName("image-title"))
                .shouldBe(visible)
                .shouldHave(text(expectedResult));
    }


    @EnumSource(MenuItem.class)
    @ParameterizedTest()
    void simpleMenuTest(MenuItem testData) {
        List<SelenideElement> menuList = elements(byClassName("navigation__link-text"));
        menuList.stream().filter(it -> it.getText().equals(testData.rusName)).findFirst().get().click();
        element(byClassName("image-title"))
                .shouldBe(visible)
                .shouldHave(text(testData.rusName));
    }


    @AfterEach
    void clearBrowser() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();

    }
}