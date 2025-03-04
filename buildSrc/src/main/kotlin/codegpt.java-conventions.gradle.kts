fun properties(key: String) = project.findProperty(key).toString()

plugins {
  id("java")
  id("idea")
  id("org.jetbrains.intellij")
}

version = properties("pluginVersion")

repositories {
  mavenCentral()
}

intellij {
  type.set(properties("platformType"))
  version.set(properties("platformVersion"))
}

dependencies {
  implementation("ee.carlrobert:llm-client:0.0.3")
}

tasks {
  properties("javaVersion").let {
    withType<JavaCompile> {
      sourceCompatibility = it
      targetCompatibility = it
    }
  }

  jar {
    archiveBaseName.set(properties("pluginName") + "-" + project.name)
  }

  runIde {
    enabled = false
  }

  verifyPlugin {
    enabled = false
  }

  runPluginVerifier {
    enabled = false
  }

  patchPluginXml {
    enabled = false
  }

  publishPlugin {
    enabled = false
  }

  signPlugin {
    enabled = false
  }
}