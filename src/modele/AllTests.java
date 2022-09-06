package modele;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestIPAddress.class, TestEquipementIP.class, TestInterfaceIP.class, TestMessageIP.class,
		TestRoute.class, TestRoutingTable.class, TestLienIP.class })
public class AllTests {

}
