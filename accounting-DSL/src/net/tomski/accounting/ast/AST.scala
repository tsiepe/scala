package net.tomski.accounting.ast

object AST {
    case class AccountAndAmount(account: Long, amount: BigDecimal)
    case class DebitSide(debitLines: Seq[AccountAndAmount])
    case class CreditSide(creditLines: Seq[AccountAndAmount])
    case class AccountingRecord(date: ValueDate, debit: DebitSide, credit: CreditSide, description: AccountingRecordDescription)
    case class ValueDate(day: Int, month: Int, year: Int)
    case class AccountingRecordDescription(description: String)
    case class AccountingRecords(accountingRecords: List[AccountingRecord]) { override def toString = accountingRecords.mkString("\n") };
}