package net.tomski.accounting.dsl

import net.tomski.accounting.ast.AST.AccountingRecord
import net.tomski.accounting.ast.AST.DebitSide
import net.tomski.accounting.ast.AST.CreditSide
import net.tomski.accounting.ast.AST.AccountAndAmount
import net.tomski.accounting.ast.AST.AccountingRecords
import net.tomski.accounting.ast.AST.AccountingRecordDescription
import net.tomski.accounting.ast.AST.ValueDate
import java.util.Calendar
import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.combinator.lexical.Scanners

object AccountingDsl extends RegexParsers {
    
    lazy val accounting_records = rep1(accounting_record) ^^ AccountingRecords
    
    lazy val accounting_record_description = """".+"""".r ^^ AccountingRecordDescription
    
    lazy val accounting_record = value_date ~ debit_side ~ credit_side ~ accounting_record_description ^^ { case v ~ d ~ c ~ desc => AccountingRecord(v, d, c, desc) }
    
    lazy val value_date = opt("""\d{2}""".r <~ "/") ~ opt("""\d{2}""".r <~ "/") ~ opt("""\d{4}""".r) ^^ { case date ~ month ~ year => 
        																 if (!date.isEmpty && !month.isEmpty && !year.isEmpty) {
																			ValueDate(date.get.toInt, month.get.toInt, year.get.toInt) 
																		 } else { 
																		    val cal = Calendar.getInstance 
																		    ValueDate(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR))
																		 } 
														   			}
    
    lazy val debit_side = rep1sep(line_item, ",") ^^ DebitSide
    
    lazy val credit_side = "an" ~> rep1sep(line_item, ",") ^^ CreditSide
    
    lazy val line_item = """\d+""".r ~ currency_amount ^^ { case acc ~ am => AccountAndAmount(acc.toLong, am) }
    
    lazy val currency_amount = """\d+""".r ~ opt("." ~ """\d+""".r) <~ """\w{3}""".r ^^ { case i ~ p => BigDecimal.apply(i + ( if (!p.isEmpty) p.get._1 + p.get._2.mkString else "")) }
    
    def parseAccountingRecords(str: String) = parse(accounting_records, str)
}