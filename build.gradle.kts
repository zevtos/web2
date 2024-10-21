plugins {
    java
    war
    id("io.freefair.lombok") version "8.0.1"
}

group = "ru.itmo"
version = "1.1"

repositories {
    mavenCentral()
}
dependencies {
    // Сервлеты
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")

    // JSP
    implementation("jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.0.0")

    // JSTL для JSP
    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:2.0.0")
    runtimeOnly("org.glassfish.web:jakarta.servlet.jsp.jstl:2.0.0")

    // Bean Validation
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")

    // Поддержка EL (Expression Language)
    implementation("org.glassfish:jakarta.el:4.0.2")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // JUnit & Mockito для тестирования
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core:3.9.0")
    testImplementation("org.mockito:mockito-junit-jupiter:3.9.0")
}
//dependencies {
//        // Сервлеты
//    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
//
//    // JSP
//    implementation("jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.0.0")
//
//    // JSTL для JSP
//    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:2.0.0")
//    runtimeOnly("org.glassfish.web:jakarta.servlet.jsp.jstl:2.0.0")
//
//    // Bean Validation
//    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
//    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
//
//    // Поддержка EL (Expression Language)
//    implementation("org.glassfish:jakarta.el:4.0.2")
//
//    // Lombok
//    compileOnly("org.projectlombok:lombok")
//    annotationProcessor("org.projectlombok:lombok")
//
//    // JUnit & Mockito для тестирования
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
//    testImplementation("org.mockito:mockito-core:3.9.0")
//    testImplementation("org.mockito:mockito-junit-jupiter:3.9.0")
//
//    testImplementation("org.mockito:mockito-core:3.9.0")
//    testImplementation("org.mockito:mockito-junit-jupiter:3.9.0")
//    // Зависимость для сервлетов (jakarta.servlet)
//    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
//    // Зависимость для JSP (jakarta.servlet.jsp)
//    implementation("jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.0.0")
//
//    // Зависимость для Jakarta EE (JPA, Bean Validation, JSF и т.д.)
//    implementation("jakarta.platform:jakarta.jakartaee-web-api:10.0.0")
//
//    // Зависимость для Bean Validation (Jakarta Validation API)
//    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
//
//    // Зависимость для Hibernate Validator (реализация Bean Validation)
//    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
//
//    // Зависимость для поддержки EL (необходима Hibernate Validator)
//    implementation("org.glassfish:jakarta.el:4.0.2")
//
////    // Зависимость для Jakarta Persistence API (JPA)
////    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
////
////    // Зависимость для EclipseLink (реализация JPA)
////    implementation("org.eclipse.persistence:eclipselink:3.0.2")
//
////    // Зависимость для PostgreSQL
////    implementation("org.postgresql:postgresql:42.5.0")
//
////    // Зависимости для PrimeFaces (для компонентов JSF)
////    implementation("org.primefaces:primefaces:12.0.0")
//
//    // Lombok
//    compileOnly("org.projectlombok:lombok")
//    annotationProcessor("org.projectlombok:lombok")
//
//    // Зависимость для тестирования (JUnit)
//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
//}

tasks.war {
    webAppDirectory.set(file("src/main/webapp"))
}

java {
    sourceSets["main"].java.srcDirs("src/main/java")
    sourceSets["main"].resources.srcDirs(
        "src/main/resources",
        "src/main/webapp"
    ) // Добавляем webapp и ресурсы как ресурсные директории
}

tasks.test {
    useJUnitPlatform()
}
