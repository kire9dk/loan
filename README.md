# Rate calculation system

Rate calculation system will allow prospective borrowers to
obtain a quote from pool of lenders for 36 month loans. This system will 
take the form of a command-line application.

It will be provided with a file containing a list of all the offers being made
by the lenders within the system in CSV format, see the example [market.csv](src/test/resources/market.csv) file
provided alongside this specification.

It will provide as low a rate to the borrower as is possible to
ensure that quotes are as competitive as they can be against competitors'. 
It will provide the borrower with the details of the
monthly repayment amount and the total repayment amount.

Repayment amounts are displayed with 2 decimals and the rate of the 
loan is displayed with one decimal.

Borrowers should be able to request a loan of any £100 increment between £1000
and £15000 inclusive. If the market does not have sufficient offers from
lenders to satisfy the loan then the system will inform the borrower that it
is not possible to provide a quote at that time.

The application should produce output in the form:

    cmd> java -jar quote-1.0-SNAPSHOT.jar [market_file] [loan_amount] 
    Requested amount: £XXXX
    Rate: X.X%
    Monthly repayment: £XXXX.XX
    Total repayment: £XXXX.XX

Examples:

	cmd> java -jar target\quote-1.0-SNAPSHOT.jar src/test/resources/market_file.csv 1000
     Requested amount: £1000
     Rate: 7.0%
     Monthly repayment: £30.78
     Total repayment: £1108.10

    cmd> java -jar target\quote-1.0-SNAPSHOT.jar src/test/resources/market_file.csv 1500
     Requested amount: £1500
     Rate: 7.1%
     Monthly repayment: £46.26
     Total repayment: £1665.39


## Remarks
 
 * The monthly and total repayment use monthly compounding interest