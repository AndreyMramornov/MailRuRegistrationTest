import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            // Открыть mail.ru
            driver.get("https://mail.ru");

            // Кликнуть в "Создать почту"
            WebElement createAccountButton = driver.findElement(By.linkText("Регистрация"));
            createAccountButton.click();

            // Нажатие на кнопку "Создать" без заполнения
            clickButton(driver, "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/button/span");
            // Вывод ошибок в консоль
            printErrors(driver, "Ошибка при создании почты без всего:");


            // Заполнение поля "Имя"
            fillField(driver, "//*[@id='fname']", "Иван");
            // Нажатие на кнопку "Создать" после заполнения имени
            clickButton(driver, "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/button/span");
            // Вывод ошибок в консоль
            printErrors(driver, "Ошибка при создании почты с именем:");


            // Заполнение поля "Фамилия"
            fillField(driver, "//*[@id=\"lname\"]", "Иванов");
            // Нажатие на кнопку "Создать" после заполнения фамилии
            clickButton(driver, "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/button/span");
            // Вывод ошибок в консоль
            printErrors(driver, "Ошибка при создании почты с фамилией:");


            // Заполнение всех полей, кроме телефона
            selectDropdownOption(driver, "//*[@id='root']/div/div[4]/div[4]/div/div/div/div/form/div[6]/div[2]/div/div/div/div[1]/div/div/div/div",
                    "//*[@id=\"react-select-2-option-0\"]/div/div/div[2]/span");
            selectDropdownOption(driver, "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[6]/div[2]/div/div/div/div[3]/div/div/div/div[1]",
                    "//*[@id=\"react-select-3-option-0\"]/div/div/div[2]/span");
            selectDropdownOption(driver, "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[6]/div[2]/div/div/div/div[5]/div/div/div/div",
                    "//*[@id=\"react-select-4-option-24\"]/div/div/div[2]/span");

            clickButton(driver, "//*[@id='root']/div/div[4]/div[4]/div/div/div/div/form/div[9]/div[2]/div/label[1]/div[1]/div[2]");

            fillField(driver, "//*[@id='aaa__input']", "test170224");
            fillField(driver, "//*[@id='password']", "240217test");
            fillField(driver, "//*[@id='repeatPassword']", "240217test");

            // Нажатие на кнопку "Создать" после заполнения всех полей
            clickButton(driver, "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/button/span");

            // Вывод ошибок в консоль
            printFullErrors(driver, "Ошибка при создании почты с полным заполнением:");

        } finally {
            //driver.quit();
        }
    }

    private static void clickButton(WebDriver driver, String locator) {
        WebElement button = driver.findElement(By.xpath(locator));
        button.click();
    }

    private static void fillField(WebDriver driver, String locator, String value) {
        WebElement field = driver.findElement(By.xpath(locator));
        field.sendKeys(value);
    }

    private static void selectDropdownOption(WebDriver driver, String dropdownLocator, String optionLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownLocator)));
        dropdown.click();

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionLocator)));
        option.click();
    }

    private static void printErrors(WebDriver driver, String message) {
        System.out.println("\n" + message);
        List<WebElement> errorFields = driver.findElements(By.className("error-0-2-102"));
        for (WebElement errorField : errorFields) {
            String errorText = errorField.getText();
            System.out.println(errorText);
        }
    }

    private static void printFullErrors(WebDriver driver, String message) {
        System.out.println("\n" + message);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        List<WebElement> errorFields = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[12]/div/div[3]/div/div[3]/small[contains(text(),'Ящик с таким именем уже существует')]")));
        for (WebElement errorField : errorFields) {
            String errorText = errorField.getText();
            System.out.println(errorText);
        }
    }
}