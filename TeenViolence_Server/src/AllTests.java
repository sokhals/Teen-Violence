import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import authentication.AuthenticatingUserTest;
import imageData.ImageDataServletTest;
import parameter.FetchInstructionTest;
import parameter.InitialParameterTest;
import questionnaire.QuestionnaireTest;
import registration.RegisterTest;

@RunWith(Suite.class)
@SuiteClasses({
	AuthenticatingUserTest.class,
	ImageDataServletTest.class,
	FetchInstructionTest.class,
	InitialParameterTest.class,
	QuestionnaireTest.class,
	RegisterTest.class
})
public class AllTests {

}
