plugins {
  java
}

java {
  sourceCompatibility = JavaVersion.VERSION_22
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(22))
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

dependencies {
  compileOnly(libs.lombok)
  annotationProcessor(libs.lombok)
  testImplementation(libs.junit)
}
