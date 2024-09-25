run:
	mvn spring-boot:run -Dspring-boot.run.profiles=dev
run-mysql:
	sh db/run-mysql-for-local.sh
build:
	mvn clean package
lint-check:
	mvn spotless:check
lint-apply:
	mvn spotless:apply
test:
	mvn clean test
show-coverage:
	mvn jacoco:report && open target/site/jacoco/index.html

