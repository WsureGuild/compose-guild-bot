package bot.tx.wsure.top.utils

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

object SeleniumUtils {
    fun chrome(): WebDriver {
        val options = ChromeOptions()
        options.addArguments("--headless")
        options.addArguments("--disable-gpu")
        options.addArguments("--window-size=1080,1920")
        WebDriverManager.getInstance()
        return ChromeDriver(options)
    }
}