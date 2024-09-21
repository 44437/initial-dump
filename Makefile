run:
	mvn spring-boot:run -Dspring-boot.run.profiles=dev
run-mysql:
	sh db/run-mysql-for-local.sh
