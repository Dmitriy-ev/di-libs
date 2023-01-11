import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

interface Account {
    fun getAmount(): Double
    fun addAmount(delta: Double)
    fun subAmount(delta: Double)
}

class RealAccount : Account { // прод стенд

    private var amount = 0.0

    override fun getAmount() = amount

    override fun addAmount(delta: Double) {
        amount += delta
    }

    override fun subAmount(delta: Double) {
        amount -= delta

    }
}

class TestAccount : Account { // тест стенд

    private var amount = 0.0

    override fun getAmount(): Double {
        println("Get amount")
        return amount
    }

    override fun addAmount(delta: Double) {
        println("Add amount")
        amount += delta
    }

    override fun subAmount(delta: Double) {
        println("Sub amount")
        amount -= delta
    }
}

class Shop: KoinComponent {

   // private val account: Account явно используем аккаунт.
    private val account: Account by inject()

    var products = 0

    fun takeProducts() {
        account.subAmount(100.0)
        products++
    }

    fun withDrawProducts() {
        account.addAmount(100.0)
        products--
    }

}
class MainClass : KoinComponent{
    private val shop : Shop by inject()

    fun shopping(){
        val shop = Shop()
        shop.takeProducts()
        shop.takeProducts()
        shop.withDrawProducts()
    }
}

fun main() {
    val accountModule = module {
        single<Account> { TestAccount() }
        single {Shop()}
    }
    startKoin{
        modules(accountModule)
    }
    MainClass().shopping()
}