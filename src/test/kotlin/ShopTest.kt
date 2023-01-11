import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.assertEquals

class ShopTest : KoinTest {
    val mainClass by inject<MainClass>()

    @Test
    fun testModule(){
        val testModule = module {
                single<Account> { TestAccount() }
                singleOf(::Shop)
                singleOf(::MainClass)
        }
        startKoin{
            modules(testModule)
        }
        mainClass.shopping()
        assertEquals(200.0, get<Account>().getAmount())
    }

}