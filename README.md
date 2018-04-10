# loan-calculator
Implementation of a system that matches funders to a loan request, and calculates repayments over a 36 month period using monthly compounding interest.

---
### Prerequisites

* Java 8
* maven

---

### Running the code

To perform a maven build and get a quote using the specified market data input file and loan amount.

`./buildAndQuote.sh <market file> <loan amount>`

e.g. `./buildAndQuote.sh market.csv 1000`

To get a quote using the specified market data input file and loan amount, using a previously built version of the code.

`./quote.sh <market file> <loan amount>`

e.g. `./quote.sh market.csv 1000`

---
### Running the tests

`mvn clean test`

---
### Additional plugins

* [GenerateTestCases](https://plugins.jetbrains.com/plugin/5847-generatetestcases) - To generate and link meaningfully named unit tests
* [Lombok](https://projectlombok.org/) - To reduce boilerplate code
