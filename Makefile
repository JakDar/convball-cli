build-native:
	export PATH=/usr/lib/jvm/java-11-graalvm/bin:${PATH};export JAVA_PATH=/usr/lib/jvm/java-11-graalvm; sbt graalvm-native-image:packageBin

just-install-native:
	mkdir -p ~/.local/bin
	cp target/graalvm-native-image/com.github.jakdar.scalaproto ~/.local/bin/scalaproto

install-native: build-native just-install-native

build:
	sbt assembly


install:
	mkdir -p ~/.local/usr/share/scalaproto
	cp ./target/scala-2.13/scalaproto.jar ~/.local/usr/share/scalaproto/scalaproto.jar

	printf '#!/bin/sh\njava -jar ~/.local/usr/share/scalaproto/scalaproto.jar "$$@"' > ~/.local/bin/scalaproto
	chmod u+x ~/.local/bin/scalaproto

test:
	scalaproto "to-proto" "case class Entity(id: Long, name: String)"
	scalaproto "to-scala" "message Entity{ required string ala = 1;}"
