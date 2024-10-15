plugins {
    java
    war
    id("io.freefair.lombok") version "8.0.1"
}

group = "ru.itmo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Зависимость для сервлетов (jakarta.servlet)
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")

    // Зависимость для JSP (jakarta.servlet.jsp)
    implementation("jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.0.0")

    // Зависимость для JSTL (если планируете использовать JSTL теги)
    implementation("javax.servlet:jstl:1.2")

    // Зависимость для тестирования (JUnit)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.war {
    webAppDirectory.set(file("src/main/webapp"))
}

java {
    sourceSets["main"].java.srcDirs("src/main/java")
    sourceSets["main"].resources.srcDirs("src/main/resources", "src/main/webapp") // Добавляем webapp и ресурсы как ресурсные директории
}

tasks.test {
    useJUnitPlatform()
}