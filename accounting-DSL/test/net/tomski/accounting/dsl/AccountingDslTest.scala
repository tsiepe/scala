package net.tomski.accounting.dsl

import AccountingDsl._

object AccountingDslTest {

  def main(args: Array[String]): Unit = { 
      val testStr = """150 100.00 EUR, 
    		  			140 20 EUR 
    		  							an 
    		  								301 120.00 EUR
    		  			"Paid car fuel for company car MZ-LK 933"
    		  			31/12/2011 234 300 EUR,
    		  			140  60 EUR
    		  							an 
    		  								301 360 EUR
    		  			"Paid winter tyres for company car MZ-UH 730""""
          
      parseAccountingRecords(testStr) match {
          case Success(accounting_records, _) => println(accounting_records)
          case ns: NoSuccess => println("Failure parsing: " + ns.msg)
      }
  }

}