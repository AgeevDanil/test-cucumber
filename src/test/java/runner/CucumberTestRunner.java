package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources", // Относительный путь к папке с feature-файлами
        glue = "steps",                  // Пакет с реализациями шагов
        plugin = {
                "pretty",                             // Форматированный вывод в консоль
                "html:target/cucumber-reports.html",  // Отчет в HTML-формате
                "json:target/cucumber.json"          // Отчет в JSON-формате
        },
        monochrome = true,                        // Читабельный вывод в консоль
        tags = "@all"                             // Тег для выполнения определенных сценариев
)
public class CucumberTestRunner {
}
