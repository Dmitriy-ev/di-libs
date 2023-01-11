import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject

@Module
abstract class AccountModule {
    @Binds
    abstract fun bindAccount(account: TestAccount2): Account2
}

@Component(modules = [AccountModule::class])
interface MainComponent {
    fun inject(shop: Shop2)
}

interface Account2 {
    fun getAmount(): Double
    fun addAmount(delta: Double)
    fun subAmount(delta: Double)
}

class RealAccount2 @Inject constructor() : Account2 { // прод стенд

    private var amount = 0.0

    override fun getAmount() = amount

    override fun addAmount(delta: Double) {
        amount += delta
    }

    override fun subAmount(delta: Double) {
        amount -= delta
    }
}

class TestAccount2 @Inject constructor() : Account2 { // тест стенд

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

class Shop2 @Inject constructor(val mainComponent: MainComponent) {

    // private val account: Account явно используем аккаунт.
    @Inject
    lateinit var account: Account2

   fun init() {
        mainComponent.inject(this)
    }

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

fun shopping(mainComponent: MainComponent) {
    val shop = Shop2(mainComponent)
    shop.init()
    shop.takeProducts()
    shop.takeProducts()
    shop.withDrawProducts()
}

fun main() {
    val mainComponent = DaggerMainComponent.create()
    shopping(mainComponent)
}