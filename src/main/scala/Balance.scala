case class Balance(
                    statement_id: Int,
                    file_id: Int,
                    cash: Int,
                    marketable_securities: Int,
                    accounts_receivable: Int,
                    inventories: Int,
                    total_current_assets: Int,
                    investments: Int,
                    fixed_assets: Int,
                    intangible_assets: Int,
                    total_non_current_assets: Int,
                    total_assets: Int,
                    future_housing_repairs_payments: Int,
                    current_liabilities: Int,
                    non_current_liabilities: Int,
                    provisions: Int,
                    equity: Int,
                    total_equities: Int,
                    var current_ratio: Long = 0
)

