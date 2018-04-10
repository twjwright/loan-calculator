mvn -q clean compile assembly:single
java -jar target/loan-calculator-1.0-SNAPSHOT-jar-with-dependencies.jar "$@"
