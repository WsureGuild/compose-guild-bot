package bot.tx.wsure.top.utils

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.io.File
import kotlin.io.path.Path

object SeleniumUtils {
    fun chrome(): WebDriver {
        val options = ChromeOptions()
        options.addArguments("--headless")
        options.addArguments("--disable-gpu")
        options.addArguments("--window-size=1080,1920")
        WebDriverManager.chromedriver().setup()
        return ChromeDriver(options)
    }

    fun getBiliDynamicImage(dynamicId:String):File {
        val dynamicImage = Path("screenshot/biliDynamic/$dynamicId.png").toAbsolutePath().toFile()
        FileUtils.createFileAndDirectory(dynamicImage)
        val driver = chrome()
        driver.get("https://t.bilibili.com/$dynamicId")
        // driver.manage().addCookie(Cookie("key", "value"))
        val card = driver.findElement(By.className("detail-card"))
        val panelArea = driver.findElement(By.className("panel-area"))
        (driver as JavascriptExecutor).executeScript("arguments[0].remove();",panelArea,)
        val vanPopover = driver.findElements(By.className("van-popover"))
        vanPopover.forEach { (driver as JavascriptExecutor).executeScript("arguments[0].remove();",it) }
        val srcFile = (card as TakesScreenshot).getScreenshotAs(OutputType.FILE)
        srcFile.copyTo(dynamicImage,true)
        driver.quit()
        return dynamicImage
    }
}