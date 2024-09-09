plugins {
  java
}

allprojects {
  repositories {
    mavenCentral()

    maven(url = "https://repo.spring.io/milestone")
    maven(url = "https://repo.spring.io/snapshot")
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }
}

subprojects {

}
