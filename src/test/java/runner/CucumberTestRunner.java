package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources", // Относительный путь к папке с feature-файлами
        glue = "steps",                  // Пакет с реализациями шагов
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json"
        },
        monochrome = true,                        // Читабельный вывод в консоль
        tags = "@all"                             // Тег для выполнения определенных сценариев
)
public class CucumberTestRunner {
}
