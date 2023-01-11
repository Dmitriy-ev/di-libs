import dagger.Binds
import dagger.Component
import dagger.Module
import kotlin.test.Test
import kotlin.test.assertEquals

@Module
interface TestAccountModule {

    @Binds
    fun bindAccount(account: TestAccount2): Account2
}

@Component(modules = [TestAccountModule::class])
abstract class TestComponent : MainComponent {

}

class ShopTest2 {

    @Test
    fun testShop() {
        val component = DaggerTestComponent.create()
        val shopping = Shop2(component)
        shopping.init()
        shopping.takeProducts()
        shopping.takeProducts()
        shopping.withDrawProducts()
        shopping.withDrawProducts()
        shopping.withDrawProducts()
        assertEquals(200.0, shopping.account.getAmount())
    }
}