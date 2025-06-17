plugins {
    id("java")
}

group = "com.prashik"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.oracle.oci.sdk:oci-java-sdk-common:3.52.0")
    implementation("com.oracle.oci.sdk:oci-java-sdk-vault:3.52.0")
    implementation("com.oracle.oci.sdk:oci-java-sdk-common-httpclient:3.52.0")
    implementation("com.oracle.oci.sdk:oci-java-sdk-addons-resteasy-client-configurator:3.52.0")
    implementation("com.oracle.oci.sdk:oci-java-sdk-common-httpclient-jersey:3.52.0")
    implementation("io.helidon.common:helidon-common-media-type:3.2.12")
    implementation("io.helidon.config:helidon-config-yaml:3.2.12")
    implementation("org.slf4j:slf4j-simple:2.0.17")
}

tasks.test {
    useJUnitPlatform()
}

